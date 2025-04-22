package com.example.stageconnect.presentation.navigation

sealed class Screen(val route: String) {
    object AppStart : Screen("appStart")
    object Onboarding : Screen("onboarding")
    object SignIn : Screen("signIn")
    object ExpertiseSelection : Screen("expertiseSelection")
    object ProfileSelection : Screen("profileSelection")
    object UserInformation : Screen("userInformation")
    object Home : Screen("home")
    object Search : Screen("search")
    object Filter : Screen("filter")
    object JobDetails : Screen("jobDetails")
    object SavedJobs : Screen("savedJobs")
    object Applications : Screen("applications")
    object Profile : Screen("profile")
    object Messaging : Screen("messaging")
    object ApplicationStatus : Screen("applicationStatus")
}
