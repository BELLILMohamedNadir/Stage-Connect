package com.example.stageconnect.presentation.navigation

sealed class Screen(val route: String) {
    object AppStart : Screen("appStart")
    object Onboarding : Screen("onboarding")
    object SignIn : Screen("signIn")
    object ExpertiseSelection : Screen("expertiseSelection")
    object ProfileSelection : Screen("profileSelection")
    object UserInformation : Screen("userInformation")
    object StudentHome : Screen("studentHome")
    object RecruiterHome : Screen("recruiterHome")
    object EstablishmentHome : Screen("establishmentHome")
    object Search : Screen("search")
    object Filter : Screen("filter")
    object JobDetails : Screen("jobDetails")
    object SavedJobs : Screen("savedJobs")
    object StudentApplications : Screen("studentApplications")
    object RecruiterApplications : Screen("recruiterApplications")
    object EstablishmentStudents : Screen("establishmentStudents")
    object Profile : Screen("profile")
    object Room : Screen("room")
    object Messaging : Screen("messaging")
    object StudentApplicationStatus : Screen("studentApplicationStatus")
    object RecruiterApplicationStatus : Screen("recruiterApplicationStatus")
    object EstablishmentApplicationStatus : Screen("establishmentApplicationStatus")
    object ProfileDetails : Screen("profileDetails")
    object ContactInformation : Screen("contactInformation")
    object Summary : Screen("summary")
    object WorkExperience : Screen("workExperience")
    object Education : Screen("education")
    object Project : Screen("project")
    object Certification : Screen("certification")
    object Internship : Screen("internship")
    object Language : Screen("language")
    object Skill : Screen("skill")
    object CvResume : Screen("cvResume")
    object AddOffer : Screen("addOffer")
    object OfferApply : Screen("offerApply")
    object OrganizationInfo : Screen("organizationInfo")
    object OrganizationProfile : Screen("organizationProfile")
    object WorkExperienceDetails : Screen("workExperienceDetails")
    object CertificationDetails : Screen("certificationDetails")
    object EducationDetails : Screen("educationDetails")
    object InternshipDetails : Screen("internshipDetails")
    object LanguageDetails : Screen("languageDetails")
    object ProjectDetails : Screen("projectDetails")
}
