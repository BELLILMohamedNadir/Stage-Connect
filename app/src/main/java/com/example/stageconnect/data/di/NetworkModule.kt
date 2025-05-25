package com.example.stageconnect.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.stageconnect.data.interceptor.AuthInterceptor
import com.example.stageconnect.data.remote.api.ApiService
import com.example.stageconnect.data.remote.api.StompWebSocketClient
import com.example.stageconnect.data.remote.repository.AuthenticationRepository
import com.example.stageconnect.data.remote.repository.AuthenticationRepositoryImpl
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import com.example.stageconnect.data.remote.repository.EstablishmentRepositoryImpl
import com.example.stageconnect.data.remote.repository.RegisterRepository
import com.example.stageconnect.data.remote.repository.RegisterRepositoryImpl
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.IP_ADDRESS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://$IP_ADDRESS/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideStompWebSocketClient(storageRepository: StorageRepository): StompWebSocketClient {
        return StompWebSocketClient(storageRepository)
    }

}
