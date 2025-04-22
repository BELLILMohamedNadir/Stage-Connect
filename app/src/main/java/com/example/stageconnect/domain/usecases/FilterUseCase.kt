package com.example.stageconnect.domain.usecases

import com.example.stageconnect.data.model.Application
import com.example.stageconnect.data.model.Offer
import javax.inject.Inject

class FilterUseCase @Inject constructor() {

    // Function to filter offers based on the searchedText
    fun filterOffers(offers: List<Offer>, searchedText: String): List<Offer> {
        return offers.filter {
            it.position.contains(searchedText, ignoreCase = true) ||
                    it.company.contains(searchedText, ignoreCase = true)
        }
    }

    fun filterApplications(offers: List<Application>, searchedText: String): List<Application> {
        return offers.filter {
            it.offer.position.contains(searchedText, ignoreCase = true) ||
                    it.offer.company.contains(searchedText, ignoreCase = true) ||
                    it.status.name.contains(searchedText, ignoreCase = true)
        }
    }
}