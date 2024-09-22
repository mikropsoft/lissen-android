package org.grakovne.lissen.ui.screens.player.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Headset
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import org.grakovne.lissen.viewmodel.PlayerViewModel

@Composable
fun PlayerNavBarComposable(
    viewModel: PlayerViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    onChaptersClick: () -> Unit
) {
    val playingQueueExpanded by viewModel.playingQueueExpanded.observeAsState(false)

    NavigationBar(
        containerColor = Color.Transparent,
        contentColor = colorScheme.onBackground,
        modifier = modifier
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = colorScheme.onBackground,
            modifier = modifier
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Outlined.Headset,
                        contentDescription = "Library"
                    )
                },
                label = { Text("Library") },
                selected = false,
                onClick = { },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = colorScheme.onBackground,
                    unselectedTextColor = colorScheme.onBackground
                )
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Outlined.Timer,
                        contentDescription = "Timer"
                    )
                },
                label = { Text("Timer") },
                selected = false,
                onClick = { },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.primary,
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Outlined.Book,
                        contentDescription = "Chapters list"
                    )
                },
                label = { Text("Chapters") },
                selected = playingQueueExpanded,
                onClick = { onChaptersClick() },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.primary,
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = "Preferences"
                    )
                },
                label = { Text("Preferences") },
                selected = false,
                onClick = { navController.navigate("settings_screen") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.primary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}