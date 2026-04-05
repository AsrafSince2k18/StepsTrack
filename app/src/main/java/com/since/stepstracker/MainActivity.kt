package com.since.stepstracker

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.stepstracker.main_config.viewmodel.MainViewModel
import com.since.stepstracker.navigation.NavGraph
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val viewmodel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !viewmodel.state.isChecking
        }
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        setContent {
            StepsTrackerTheme {
                val navHostController = rememberNavController()
                    if(viewmodel.state.isChecking) {
                        NavGraph(
                            navHostController = navHostController,
                            isLoggedIn = true
                        )
                    }

            }
        }
    }
}
