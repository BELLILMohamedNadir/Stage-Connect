package com.example.stageconnect.domain.usecases

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.domain.model.Application
import com.example.stageconnect.domain.model.Offer
import javax.inject.Inject

class FilterUseCase @Inject constructor() {

    // Function to filter offers based on the searchedText
    fun filterOffers(offers: List<OfferDto>, searchedText: String): List<OfferDto> {
        return offers.filter {
            it.position.contains(searchedText, ignoreCase = true) ||
                    it.company!!.contains(searchedText, ignoreCase = true)
        }
    }

    fun filterRooms(offers: List<RoomDto>, searchedText: String): List<RoomDto> {
        return offers.filter {
            it.sender.contains(searchedText, ignoreCase = true)
        }
    }

    fun filterApplications(applications: List<ApplicationDto>, searchedText: String): List<ApplicationDto> {
        return applications.filter {
            it.offerDto!!.company!!.contains(searchedText, ignoreCase = true) ||
                    it.status.name.contains(searchedText, ignoreCase = true)
        }
    }
}