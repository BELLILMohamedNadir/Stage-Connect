package com.example.stageconnect.domain.usecases.filter

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.data.dtos.StudentDto
import javax.inject.Inject

class FilterUseCase @Inject constructor() {

    fun filterOffers(
        offers: List<OfferDto>,
        searchedText: String,
        selectedFilters: Map<String, List<String>>
    ): List<OfferDto> {
        return offers.filter { offer ->
            // Text search (position or company)
            val matchesText = searchedText.isBlank() ||
                    (offer.position?.contains(searchedText, ignoreCase = true) == true) ||
                    (offer.company?.contains(searchedText, ignoreCase = true) == true)

            // Location
            val matchesLocation = selectedFilters["Location"]?.let { selected ->
                selected.any { location -> offer.location?.contains(location, ignoreCase = true) == true }
            } ?: true

            // Salary Range
            val matchesSalary = selectedFilters["SalaryRange"]?.firstOrNull()?.let { rangeString ->
                val (min, max) = rangeString.split("-").map { it.trim().removeSuffix("€").toLongOrNull() }
                val start = offer.salaryStart
                val end = offer.salaryEnd
                if (min != null && max != null && start != null && end != null) {
                    start in min..max || end in min..max
                } else {
                    true
                }
            } ?: true

            // Salary Unit
            val matchesSalaryType = selectedFilters["salaryUnit"]?.let { selected ->
                selected.contains(offer.salaryUnit)
            } ?: true

            // Work Type
            val matchesWorkType = selectedFilters["Work Type"]?.let { selected ->
                selected.contains(offer.workType)
            } ?: true

            // Job Level
            val matchesJobLevel = selectedFilters["Job Level"]?.let { selected ->
                offer.jobLevels?.any { it in selected } == true
            } ?: true

            // Employment Type
            val matchesEmploymentType = selectedFilters["Employment Type"]?.let { selected ->
                offer.employmentTypes?.any { it in selected } == true
            } ?: true

            // Experience
            val matchesExperience = selectedFilters["Experience"]?.let { selected ->
                selected.contains(offer.experience)
            } ?: true

            // Education
            val matchesEducation = selectedFilters["Education"]?.let { selected ->
                offer.education?.any { it in selected } == true
            } ?: true

            // Job Function
            val matchesJobFunction = selectedFilters["Job Function"]?.let { selected ->
                offer.jobFunction?.any { it in selected } == true
            } ?: true

            // Combine all
            matchesText && matchesLocation && matchesSalary && matchesSalaryType &&
                    matchesWorkType && matchesJobLevel && matchesEmploymentType &&
                    matchesExperience && matchesEducation && matchesJobFunction
        }
    }

    fun filterOffers(
        offers: List<OfferDto>,
        filter: String
    ): List<OfferDto> {
        return offers.filter { offer ->
            val normalizedFilter = filter.trim().lowercase()

            val matchesText = filter.isBlank() || when (normalizedFilter) {
                "onsite" -> offer.workType?.contains("onsite", ignoreCase = true) == true
                "remote" -> offer.workType?.contains("remote", ignoreCase = true) == true
                "hybrid" -> offer.workType?.contains("hybrid", ignoreCase = true) == true
                else -> offer.workType?.contains(normalizedFilter, ignoreCase = true) == true ||
                        offer.employmentTypes?.any { it.contains(normalizedFilter, ignoreCase = true) } == true
            }
            matchesText
        }
    }



    fun filterEstablishmentStudents(students: List<StudentDto>, searchedText: String): List<StudentDto> {
        return students.filter {
            (it.name?:"").contains(searchedText, ignoreCase = true) ||
                    (it.firstName?:"").contains(searchedText, ignoreCase = true) ||
                    (it.currentPosition?:"").contains(searchedText, ignoreCase = true)
        }
    }

    fun filterRooms(offers: List<RoomDto>, searchedText: String): List<RoomDto> {
        return offers.filter {
            it.sender.contains(searchedText, ignoreCase = true)
        }
    }

    fun filterApplications(applications: List<ApplicationDto>, searchedText: String): List<ApplicationDto> {
        return applications.filter {
            it.studentName.contains(searchedText, ignoreCase = true) ||
                it.offerDto?.position?.contains(searchedText, ignoreCase = true)!! ||
                    (it.companyName?:"").contains(searchedText, ignoreCase = true) ||
                    it.status.name.contains(searchedText, ignoreCase = true)
        }
    }
}