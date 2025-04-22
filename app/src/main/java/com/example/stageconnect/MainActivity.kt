package com.example.stageconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stageconnect.presentation.components.BottomNavigationBar
import com.example.stageconnect.presentation.components.CustomTopAppBar
import com.example.stageconnect.presentation.navigation.AppNavHost
import com.example.stageconnect.presentation.navigation.Screen
import com.example.stageconnect.ui.theme.StageConnectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define which screens hide the bars
        val screensWithNoTopBar = listOf(
            Screen.AppStart.route, Screen.Onboarding.route, Screen.SignIn.route, Screen.Home.route, Screen.Search.route
        )
        val screensWithNoBottomBar = listOf(
            Screen.AppStart.route, Screen.ApplicationStatus.route, Screen.JobDetails.route,
            Screen.Onboarding.route, Screen.SignIn.route, Screen.Filter.route
        )

        setContent {
            StageConnectTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showTopBar by remember(currentRoute) {
                    derivedStateOf { currentRoute !in screensWithNoTopBar }
                }
                val showBottomBar by remember(currentRoute) {
                    derivedStateOf { currentRoute !in screensWithNoBottomBar }
                }

                Scaffold(
                    topBar = {
                        AnimatedVisibility(visible = showTopBar) {
                            CustomTopAppBar(
                                onDismiss = {
                                    when (currentRoute) {
                                        Screen.ProfileSelection.route -> finish()
                                        Screen.ExpertiseSelection.route -> navigateBack(navController, Screen.ExpertiseSelection.route, Screen.ProfileSelection.route)
                                        Screen.UserInformation.route -> navigateBack(navController, Screen.UserInformation.route, Screen.ExpertiseSelection.route)
                                        Screen.Filter.route -> navController.popBackStack()
                                        Screen.SavedJobs.route -> navigateBack(navController, Screen.SavedJobs.route, Screen.Home.route)
                                        Screen.JobDetails.route -> navController.popBackStack()
                                        Screen.Applications.route -> navigateBack(navController, Screen.Applications.route, Screen.Home.route)
                                        Screen.Profile.route -> navigateBack(navController, Screen.Profile.route, Screen.Home.route)
                                        Screen.ApplicationStatus.route -> navController.popBackStack()
                                    }
                                },
                                navigationIcon = if (currentRoute == Screen.Filter.route)
                                    R.drawable.ic_close else R.drawable.ic_arrow_back,
                                onLeadingIconClick = { /* Optional */ },
                                title = when (currentRoute) {
                                    Screen.Filter.route -> stringResource(R.string.filter_options)
                                    Screen.SavedJobs.route -> stringResource(R.string.saved_jobs)
                                    Screen.Applications.route -> stringResource(R.string.applications)
                                    Screen.ApplicationStatus.route -> stringResource(R.string.application_status)
                                    Screen.Profile.route -> stringResource(R.string.profile)
                                    else -> ""
                                }
                            )
                        }
                    },
                    bottomBar = {
                        AnimatedVisibility(visible = showBottomBar) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }

    private fun navigateBack(navController: NavHostController, route: String, destination: String) {
        navController.navigate(destination) {
            popUpTo(route) { inclusive = true }
        }
    }
}
