package com.example.stageconnect.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stageconnect.data.model.enum.ApplicationStatus
import com.example.stageconnect.presentation.screens.applications.screens.ApplicationsScreen
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.presentation.screens.applicationstatus.screens.ApplicationStatus
import com.example.stageconnect.presentation.screens.appstart.AppStartScreen
import com.example.stageconnect.presentation.screens.filter.screens.FilterScreen
import com.example.stageconnect.presentation.screens.home.screens.HomeScreen
import com.example.stageconnect.presentation.screens.jobdetails.components.JobDetailsViewModel
import com.example.stageconnect.presentation.screens.jobdetails.screens.JobDetailsScreen
import com.example.stageconnect.presentation.screens.messaging.screens.MessagingScreen
import com.example.stageconnect.presentation.screens.onboarding.screen.OnboardingScreen
import com.example.stageconnect.presentation.screens.profile.screens.ProfileScreen
import com.example.stageconnect.presentation.screens.savedjobs.screens.SavedJobsScreen
import com.example.stageconnect.presentation.screens.search.screens.SearchScreen
import com.example.stageconnect.presentation.screens.signin.SignInScreen
import com.example.stageconnect.presentation.screens.signup.screens.ExpertiseSelectionScreen
import com.example.stageconnect.presentation.screens.signup.screens.ProfileSelectionScreen
import com.example.stageconnect.presentation.screens.signup.screens.UserInformationScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    val jobDetailsViewModel: JobDetailsViewModel = hiltViewModel()
    val applicationViewModel: ApplicationViewModel = hiltViewModel()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            AppStartScreen(
                onClick = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.AppStart.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(
                onForgetPassword = {},
                onSignIn = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                           },
                onSignUp = {navController.navigate(Screen.ProfileSelection.route)},
                onSignUpWithGoogle = {})
        }
        composable(route = Screen.ProfileSelection.route) {
            ProfileSelectionScreen(
                onAnIntern = {},
                onARecruiter = {},
                onAResponsible = {},
                onNext = {
                    navController.navigate(Screen.ExpertiseSelection.route)
                }
            )
        }
        composable(route = Screen.ExpertiseSelection.route) {
            ExpertiseSelectionScreen(
                onNext = {
                    navController.navigate(Screen.UserInformation.route)
                }
            )
        }
        composable(route = Screen.UserInformation.route) {
            UserInformationScreen(
                onNext = {navController.navigate(Screen.Home.route)}
            )
        }
        composable(
            route = Screen.Home.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 200)) +
                        slideInHorizontally(initialOffsetX = { 30 }, animationSpec = tween(200))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(200)) +
                        slideOutHorizontally(targetOffsetX = { -30 }, animationSpec = tween(200))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(200)) +
                        slideInHorizontally(initialOffsetX = { -30 }, animationSpec = tween(200))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(200)) +
                        slideOutHorizontally(targetOffsetX = { 30 }, animationSpec = tween(200))
            }
        ) {
            HomeScreen(
                jobDetailsViewModel = jobDetailsViewModel,
                onFilterClick = {
                navController.navigate(Screen.Filter.route)
            },
                onOfferCardClick = {
                    navController.navigate(Screen.JobDetails.route)
                }){
                navController.navigate(Screen.Search.route){
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
        }

        composable(route = Screen.Search.route) {
            SearchScreen(
                jobDetailsViewModel = jobDetailsViewModel,
                onFilterClick = {
                navController.navigate(Screen.Filter.route)
            },
                onOfferCardClick = {
                    navController.navigate(Screen.JobDetails.route)
                }){
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Search.route) { inclusive = true }
                }
            }
        }

        composable(route = Screen.Filter.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 200)) +
                        slideInHorizontally(initialOffsetX = { 30 }, animationSpec = tween(200))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(200)) +
                        slideOutHorizontally(targetOffsetX = { -30 }, animationSpec = tween(200))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(200)) +
                        slideInHorizontally(initialOffsetX = { -30 }, animationSpec = tween(200))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(200)) +
                        slideOutHorizontally(targetOffsetX = { 30 }, animationSpec = tween(200))
            }) {
            FilterScreen()
        }

        composable(route = Screen.JobDetails.route) {
            JobDetailsScreen(
                jobDetailsViewModel = jobDetailsViewModel
            )
        }

        composable(route = Screen.SavedJobs.route) {
            SavedJobsScreen(
                jobDetailsViewModel = jobDetailsViewModel,
                onFilterClick = {
                    navController.navigate(Screen.Filter.route)
                },
                ){
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Search.route) { inclusive = true }
                }
            }
        }

        composable(route = Screen.Applications.route) {
            ApplicationsScreen(
                applicationViewModel = applicationViewModel,
                onFilterClick = {
                    navController.navigate(Screen.Filter.route)
                },
                onNavigate = {
                    navController.navigate(Screen.ApplicationStatus.route) {
                    }
                }
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }

        composable(route = Screen.Messaging.route) {
            MessagingScreen()
        }

        composable(route = Screen.ApplicationStatus.route) {
            ApplicationStatus(
                applicationViewModel = applicationViewModel
            )
        }
    }
}
