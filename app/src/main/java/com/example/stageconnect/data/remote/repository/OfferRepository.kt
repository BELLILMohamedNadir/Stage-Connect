package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface OfferRepository {
    suspend fun createOffer(offerDto: OfferDto): OfferDto
    suspend fun updateOffer(id: Long, offerDto: OfferDto): OfferDto
    suspend fun getAllOffer(studentId: Long): List<OfferDto>
    suspend fun getAllRecruiterOffer(recruiterId: Long): List<OfferDto>
    suspend fun getAllSavedOffer(studentId: Long): List<OfferDto>
    suspend fun saveOffer(offerId: Long, studentId: Long)
    suspend fun unSaveOffer(offerId: Long, studentId: Long)
}

// Repository Implementation
class OfferRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : OfferRepository {
    override suspend fun createOffer(offerDto: OfferDto): OfferDto {
        return apiService.createOffer(offerDto)
    }

    override suspend fun updateOffer(id: Long, offerDto: OfferDto): OfferDto {
        return apiService.updateOffer(id, offerDto)
    }

    override suspend fun getAllOffer(studentId: Long): List<OfferDto> {
        return apiService.getAllOffers(studentId)
    }

    override suspend fun getAllRecruiterOffer(recruiterId: Long): List<OfferDto> {
        return apiService.getAllRecruiterOffers(recruiterId)
    }

    override suspend fun getAllSavedOffer(studentId: Long): List<OfferDto> {
        return apiService.getAllSavedOffers(studentId)
    }

    override suspend fun saveOffer(offerId: Long, studentId: Long) {
        apiService.saveOffer(offerId, studentId)
    }

    override suspend fun unSaveOffer(offerId: Long, studentId: Long) {
        apiService.unSaveOffer(offerId, studentId)
    }
}
