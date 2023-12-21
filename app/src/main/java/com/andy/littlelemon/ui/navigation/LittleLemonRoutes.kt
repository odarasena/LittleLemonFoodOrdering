package com.andy.littlelemon.ui.navigation

sealed class LittleLemonRoutes(val route: String) {
    data object Onboarding: LittleLemonRoutes("onboarding")
    data object Home: LittleLemonRoutes("home")
    data object Profile: LittleLemonRoutes("profile")
}