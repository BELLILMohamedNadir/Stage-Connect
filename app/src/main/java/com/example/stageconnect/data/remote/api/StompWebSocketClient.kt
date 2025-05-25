package com.example.stageconnect.data.remote.api

import android.content.SharedPreferences
import android.media.session.MediaSession.Token
import android.util.Log
import com.example.stageconnect.data.dtos.MessageDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT
import com.example.stageconnect.domain.CONSTANT.IP_ADDRESS
import com.example.stageconnect.domain.CONSTANT.JWT_TOKEN
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StompWebSocketClient @Inject constructor(
    private val storageRepository: StorageRepository
)  {

    private val client = OkHttpClient.Builder()
        .pingInterval(10, TimeUnit.SECONDS)
        .build()

    private lateinit var webSocket: WebSocket

    private val _messages = MutableStateFlow<List<MessageDto>>(emptyList())
    val messages: StateFlow<List<MessageDto>> get() = _messages

    fun connect(roomId: UUID) {
        val request = Request.Builder()
            .url("ws://$IP_ADDRESS/ws-chat")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocket", "Connected")

                // STOMP CONNECT frame
                val connectFrame = buildStompFrame(
                    command = "CONNECT",
                    headers = mapOf(
                        "accept-version" to "1.2",
                        "heart-beat" to "10000,10000",
                        "Authorization" to "Bearer ${storageRepository.get(JWT_TOKEN)}"
                    )
                )
                webSocket.send(connectFrame)

                val subscribeFrame = buildStompFrame(
                    command = "SUBSCRIBE",
                    headers = mapOf(
                        "id" to "sub-0",
                        "destination" to "/topic/chat/$roomId"
                    )
                )
                webSocket.send(subscribeFrame)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("STOMP", "Raw message:\n$text")

                try {
                    // Split into headers and body
                    val parts = text.split("\n\n", limit = 2)
                    if (parts.size != 2) {
                        throw Exception("Invalid STOMP message format")
                    }

                    // Extract and clean JSON body
                    val jsonBody = parts[1]
                        .replace("??", "")  // Remove trailing garbage
                        .trim()             // Trim whitespace
                        .replace("\u0000", "") // Remove STOMP null byte

                    Log.d("STOMP", "Cleaned JSON: $jsonBody")

                    if (jsonBody.isNotBlank()){
                        val messageDto = parseMessage(jsonBody)
                        onMessageObserve(messageDto)
                    }

                } catch (e: Exception) {
                    Log.e("STOMP", "Failed to parse: ${e.message}")
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WebSocket", "Closing: $code / $reason")
                webSocket.close(1000, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocket", "Error: ${t.message}", t)
            }
        })
    }

    fun disconnect() {
        webSocket.close(1000, "Goodbye")
    }

    fun sendMessage(messageDto: MessageDto, roomId: UUID) {
        val messageBody = """{"roomId":"$roomId","content":"${messageDto.content}","senderId":"${messageDto.senderId}"}"""
        val sendFrame = buildStompFrame(
            command = "SEND",
            headers = mapOf(
                "destination" to "/app/chat.sendMessage",
                "content-type" to "application/json"
            ),
            body = messageBody
        )
        webSocket.send(sendFrame)
    }

    private fun onMessageObserve(messageDto: MessageDto) {
        _messages.update { currentMessages ->
            currentMessages + messageDto
        }
    }

    fun parseMessage(text: String): MessageDto {
        return Gson().fromJson(text, MessageDto::class.java)
    }

    private fun buildStompFrame(
        command: String,
        headers: Map<String, String> = emptyMap(),
        body: String = ""
    ): String {
        val sb = StringBuilder()
        sb.append(command).append('\n')
        for ((key, value) in headers) {
            sb.append("$key:$value\n")
        }
        sb.append('\n')
        sb.append(body)
        sb.append('\u0000') // Null byte to terminate STOMP frame
        return sb.toString()
    }
}
