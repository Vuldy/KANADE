package com.projects.kanade.ui.screen.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.projects.kanade.ui.screen.navigation.Screen

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

