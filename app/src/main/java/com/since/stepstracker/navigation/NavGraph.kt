package com.since.stepstracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.since.presentaction.introscreen.screen.IntroScreenRoot
import com.since.presentaction.login_screen.screen.LoginScreenRoot
import com.since.presentaction.registerscreen.screen.RegisterScreenRoot
import com.since.run.presentaction.active_run.screen.ActiveRunScreenRoot
import com.since.run.presentaction.active_run.service.TimerForegroundService.Companion.startService
import com.since.run.presentaction.active_run.service.TimerForegroundService.Companion.stopService
import com.since.run.presentaction.over_view.screen.RunOverViewScreenRoot
import com.since.stepstracker.MainActivity

const val AUTH_ROUTE="auth"
const val HOME_ROUTE="home"

@Composable
fun NavGraph(
    isLoggedIn: Boolean,
    navHostController: NavHostController
) {


    NavHost(
        navController = navHostController,
        startDestination = if(isLoggedIn)HOME_ROUTE else AUTH_ROUTE
    ){
        authGraph(navHostController=navHostController)
        runGraph(navHostController=navHostController)
    }

}


private fun NavGraphBuilder.authGraph(
    navHostController: NavHostController
){

    navigation(
        startDestination = ScreenRoot.IntroScreen.root,
        route = AUTH_ROUTE
    ){

        composable(route = ScreenRoot.IntroScreen.root){
            IntroScreenRoot(
                onRegister = {
                    navHostController.navigate(ScreenRoot.RegisterScreen.root)
                },
                onLogin = {
                    navHostController.navigate(ScreenRoot.LoginScreen.root)
                }
            )
        }


        composable(route= ScreenRoot.RegisterScreen.root){
            RegisterScreenRoot(
                onLoginScreen = {
                    navHostController.navigate(ScreenRoot.LoginScreen.root){
                        popUpTo(ScreenRoot.RegisterScreen.root){
                            inclusive=true
                            saveState=true
                        }
                        restoreState=true
                    }
                },
                onRegisterSuccess = {
                    navHostController.navigate(ScreenRoot.LoginScreen.root){
                        popUpTo(ScreenRoot.RegisterScreen.root){
                            inclusive=true
                        }
                    }
                }
            )
        }


        composable(route= ScreenRoot.LoginScreen.root){
            LoginScreenRoot(
                onRegisterScreen = {
                    navHostController.navigate(ScreenRoot.RegisterScreen.root){
                        popUpTo(ScreenRoot.LoginScreen.root){
                            inclusive=true
                            saveState=true
                        }
                        restoreState=true
                    }
                },
                onLoginSuccess = {
                    navHostController.navigate(ScreenRoot.HomeScreen.root){
                        popUpTo(route = AUTH_ROUTE){
                            inclusive=true
                        }
                    }
                }
            )
        }
    }
}


private fun NavGraphBuilder.runGraph(navHostController: NavHostController){

    navigation(
        startDestination = ScreenRoot.HomeScreen.root,
        route = HOME_ROUTE
    ){
        composable(route = ScreenRoot.HomeScreen.root){
            RunOverViewScreenRoot(
                onActiveRunScreen = {
                    navHostController.navigate(ScreenRoot.RunScreen.root)
                }
            )
        }


        composable(ScreenRoot.RunScreen.root,
            deepLinks = listOf(
                navDeepLink{
                    this.uriPattern="steps://tracker"
                }
            )

            ){

            val context = LocalContext.current

            ActiveRunScreenRoot(
                onHomeScreen = {
                    navHostController.navigateUp()
                },
                serviceStatus = {service ->
                    if(service){
                        val intent= startService(context, MainActivity::class.java)
                        context.startService(intent)
                    }else{
                        val intent= stopService(context)
                        context.startService(intent)
                    }
                }
            )
        }

    }

}