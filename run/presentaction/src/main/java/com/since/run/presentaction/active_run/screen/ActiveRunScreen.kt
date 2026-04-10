package com.since.run.presentaction.active_run.screen

import android.Manifest
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.since.presentaction.designsystem.component.btn.RunsFullButton
import com.since.presentaction.designsystem.component.btn.RunsOutlineButton
import com.since.presentaction.designsystem.ui.theme.RunsDarkRed
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.ui.run.utlity.coarseLocationPermission
import com.since.presentaction.ui.run.utlity.fineLocationPermission
import com.since.presentaction.ui.run.utlity.goToAppSetting
import com.since.presentaction.ui.run.utlity.notificationPermission
import com.since.presentaction.ui.run.utlity.shouldShowLocationRational
import com.since.presentaction.ui.run.utlity.shouldShowNotificationRational
import com.since.run.presentaction.active_run.component.RunsBottomScaffold
import com.since.run.presentaction.active_run.component.RunsDialog
import com.since.run.presentaction.active_run.component.map.CustomGoogleMap
import com.since.run.presentaction.active_run.state_action.ActiveRunAction
import com.since.run.presentaction.active_run.state_action.ActiveRunState
import com.since.run.presentaction.active_run.viewmodel.ActiveRunViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel(),
    onHomeScreen: () -> Unit,
    serviceStatus: (service: Boolean) -> Unit
) {

    ActiveRunScreen(
        state = viewModel.state,
        action = { action ->
            when (action) {
                ActiveRunAction.GoBackHomeScreen -> {
                    onHomeScreen()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        },
        serviceStatus = {
            serviceStatus(it)
        })

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRunScreen(
    state: ActiveRunState,
    action: (ActiveRunAction) -> Unit,
    serviceStatus: (service: Boolean) -> Unit
) {

    val context = LocalContext.current
    val componentActivity = context as ComponentActivity

    val launchPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perm ->


        val notificationRational = componentActivity.shouldShowNotificationRational()
        val locationRational = componentActivity.shouldShowLocationRational()

        val notificationPermission = perm[Manifest.permission.POST_NOTIFICATIONS] == true
        val fineLocationPermission = perm[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseLocationPermission = perm[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        action(
            ActiveRunAction.NotificationInfo(
                rational = notificationRational,
                permission = notificationPermission
            )
        )
        action(
            ActiveRunAction.LocationInfo(
                rational = locationRational,
                permission = fineLocationPermission && coarseLocationPermission
            )
        )


        /*when{
            (!notificationPermission && (!fineLocationPermission && !coarseLocationPermission)) -> {
                action(
                    ActiveRunAction.NotificationInfo(
                        rational = notificationRational,
                        permission = context.notificationPermission()
                    )
                )
                action(
                    ActiveRunAction.LocationInfo(
                        rational = locationRational,
                        permission = context.coarseLocationPermission() && context.fineLocationPermission()
                    )
                )
            }

            !fineLocationPermission && !coarseLocationPermission -> {
                action(
                    ActiveRunAction.LocationInfo(
                        rational = locationRational,
                        permission = context.coarseLocationPermission() && context.fineLocationPermission()
                    )
                )
            }

            !notificationPermission ->{
                action(
                    ActiveRunAction.NotificationInfo(
                        rational = notificationRational,
                        permission = context.notificationPermission()
                    )
                )
            }
        }*/
    }


    if (state.notificationRational || state.locationRational) {
        val content = when {
            (state.locationRational && state.notificationRational) -> {
                "Location and Notification permissions are required to track your live location and updates."
            }

            state.locationRational -> {
                "Location permission is required to track your live location."
            }

            else -> {
                "Notification permission is required to show tracking alerts."
            }
        }


        RunsDialog(
            title = "Permission",
            content = content,
            primaryButton = {
                RunsFullButton(
                    hint = "Ok",
                    isEnable = true,
                    onClick = {
                        launchPermission.requestPermission(context = context)
                    })
            })

    }


    if ((state.notificationPermissionValid || state.locationPermissionValid)) {
        val content = when {
            (state.notificationPermissionValid && state.locationPermissionValid) -> {
                "Enable Location and Notification permissions in App Settings to continue live tracking."
            }

            state.locationPermissionValid -> {
                "Enable Location permission in App Settings to use live tracking."
            }

            else -> {
                "Enable Notification permission in App Settings to receive tracking updates."
            }
        }

        RunsDialog(
            title = "Permission Required",
            content = content,
            primaryButton = {
                RunsFullButton(
                    hint = "Open Settings", isEnable = true, onClick = {
                        context.goToAppSetting()
                    })
            })
    }

    SideEffect {
        val notificationPermission = context.notificationPermission()
        val locationPermission =
            context.fineLocationPermission() && context.coarseLocationPermission()
        if (notificationPermission && locationPermission) {
            action(
                ActiveRunAction.NotificationInfo(
                    rational = context.shouldShowNotificationRational(),
                    permission = context.notificationPermission()
                )
            )

            action(
                ActiveRunAction.LocationInfo(
                    rational = context.shouldShowLocationRational(),
                    permission = context.coarseLocationPermission() && context.fineLocationPermission()
                )
            )
            action(ActiveRunAction.ClearPermissionDialog)
        }
    }


    val lifeCycle = LocalLifecycleOwner.current
    DisposableEffect(lifeCycle.lifecycle) {
        val observe = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                val notificationPermission = context.notificationPermission()
                val locationPermission =
                    context.fineLocationPermission() && context.coarseLocationPermission()
                if (notificationPermission && locationPermission) {
                    action(
                        ActiveRunAction.NotificationInfo(
                            rational = context.shouldShowNotificationRational(),
                            permission = context.notificationPermission()
                        )
                    )

                    action(
                        ActiveRunAction.LocationInfo(
                            rational = context.shouldShowLocationRational(),
                            permission = context.coarseLocationPermission() && context.fineLocationPermission()
                        )
                    )
                    action(ActiveRunAction.ClearPermissionDialog)
                }else{
                    launchPermission.requestPermission(context)
                }
            }
        }

        lifeCycle.lifecycle.addObserver(observe)
        onDispose {
            lifeCycle.lifecycle.removeObserver(observe)
        }

    }


    val bottomSheet = rememberBottomSheetScaffoldState()


    LaunchedEffect(state.activeRunOrNot) {
        if (state.activeRunOrNot) {
            serviceStatus(true)
        } else {
            serviceStatus(false)
        }
    }



    state.stopRun?.let {stop->
        if (!stop) {
            RunsDialog(
                title = "Run Paused",
                content = "Your run is currently paused. Tap Stop to finish the run or resume to continue tracking.",
                primaryButton = {
                    RunsOutlineButton(
                        modifier = it.weight(1f),
                        hint = "Stop Run",
                        isEnable = true,
                        onClick = {
                            action(ActiveRunAction.StopRun)
                        }
                    )
                },
                secondaryButton = {
                    RunsFullButton(
                        modifier = it.weight(1f),
                        hint = "Resume",
                        isEnable = true
                    ) {
                        action(ActiveRunAction.StartRun)
                    }
                }
            )
        }
    }


    LaunchedEffect(true) {
        bottomSheet.bottomSheetState.expand()
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheet,
        containerColor = MaterialTheme.colorScheme.background,
        sheetPeekHeight = 112.dp,
        sheetShape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 18.dp)
                    .width(38.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
        },
        snackbarHost = {
            val bottomPadding =
                if (bottomSheet.bottomSheetState.currentValue == SheetValue.Expanded)
                    80.dp
                else 0.dp

            state.networkStatus?.let { status ->
                AnimatedVisibility(
                    modifier = Modifier
                        .then(if (bottomSheet.bottomSheetState.isVisible) Modifier.padding(bottom = bottomPadding) else Modifier),
                    visible = !status,
                    enter = fadeIn(tween(1000)),
                    exit = fadeOut(tween(1000))
                ) {
                    Snackbar(
                        modifier = Modifier
                            .height(IntrinsicSize.Min),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                        containerColor = if (status) MaterialTheme.colorScheme.primary else RunsDarkRed,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        content = {
                            val value = when {
                                !status -> "No connection"
                                else -> "Back online"
                            }
                            Text(
                                text = value,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    )

                }
            }

        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .then(
                        if (bottomSheet.bottomSheetState.isVisible && state.networkStatus == false) Modifier.padding(
                            bottom = 90.dp
                        ) else Modifier
                    )
                    .padding(bottom = 32.dp, top = 12.dp)
            ) {
                RunsBottomScaffold(
                    time = state.currentTime,
                    elapsedTime = state.elapsedTime,
                    runData = state.runData,
                    isTracking = state.activeRunOrNot,
                    onStart = {
                        action(ActiveRunAction.StartRun)
                    })

            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {


                CustomGoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    location = state.location,
                    listOfListLocation = state.runData.listOfListLocation,
                    runningStatus = state.activeRunOrNot
                )


                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ), onClick = {
                        action(ActiveRunAction.GoBackHomeScreen)
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(paddingValues)
                        .padding(
                            start = 22.dp,
                            top = WindowInsets.statusBars
                                .asPaddingValues()
                                .calculateTopPadding() + 12.dp
                        )

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                }


            }

        }
    )

}


private fun ActivityResultLauncher<Array<String>>.requestPermission(context: Context) {

    val notificationPermission = context.notificationPermission()
    val locationPermission = context.coarseLocationPermission() && context.fineLocationPermission()

    when {
        !notificationPermission && !locationPermission -> {
            launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            )
        }

        !locationPermission -> {
            launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }

        !notificationPermission -> {
            launch(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS)
            )
        }

    }

}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    StepsTrackerTheme {
        ActiveRunScreen(
            state = ActiveRunState(), action = {},
            serviceStatus = {})
    }
}
