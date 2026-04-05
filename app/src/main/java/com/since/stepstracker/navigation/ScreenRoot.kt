package com.since.stepstracker.navigation

sealed class ScreenRoot(val root:String) {

    data object IntroScreen : ScreenRoot(root = "intro_screen")
    data object RegisterScreen : ScreenRoot(root = "register_screen")
    data object LoginScreen : ScreenRoot(root = "login_screen")

    data object HomeScreen : ScreenRoot(root = "home_screen")
    data object RunScreen : ScreenRoot(root = "run_screen")


}