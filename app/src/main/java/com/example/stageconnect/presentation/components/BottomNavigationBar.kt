package com.example.stageconnect.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stageconnect.R
import com.example.stageconnect.presentation.navigation.Screen
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.DarkGray

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    val items = listOf(
        Triple(
            painterResource(id = R.drawable.ic_home_bottom_navigation),
            stringResource(id = R.string.home),
            Screen.Home.route
        ),
        Triple(
            painterResource(id = R.drawable.ic_save_bottom_navigation),
            stringResource(id = R.string.saved_jobs),
            Screen.SavedJobs.route
        ),
        Triple(
            painterResource(id = R.drawable.ic_bag_bottom_navigation),
            stringResource(id = R.string.applications),
            Screen.Applications.route
        ),
        Triple(
            painterResource(id = R.drawable.ic_message_bottom_navigation),
            stringResource(id = R.string.message),
            Screen.Messaging.route
        ),
        Triple(
            painterResource(id = R.drawable.ic_profile_bottom_navigation),
            stringResource(id = R.string.profile),
            Screen.Profile.route
        )
    )

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        containerColor = Color.White,
    ) {
        items.forEach { (icon, label, route) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = icon,
                        contentDescription = label,
                        modifier = Modifier.size(22.dp),
                        tint = if (currentRoute == route) Blue else Color.Unspecified
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontWeight = FontWeight.W700,
                        fontSize = 10.sp,
                        color = if (currentRoute == route) Blue else DarkGray,
                        modifier = Modifier.offset(y = -(4).dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Transparent,
                    unselectedIconColor = Color.Black.copy(alpha = 0.4f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
