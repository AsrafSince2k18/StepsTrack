package com.since.run.presentaction.over_view.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.since.presentaction.designsystem.component.run.RunsFabBtn
import com.since.presentaction.designsystem.component.run.RunsScaffold
import com.since.presentaction.designsystem.component.run.RunsTopAppBar
import com.since.presentaction.designsystem.ui.theme.RunIcon
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.designsystem.ui.theme.Yellow
import com.since.presentaction.ui.ui_error.ObservableEvent
import com.since.run.presentaction.over_view.event.OverViewEvent
import com.since.run.presentaction.over_view.viewmodel.OverViewViewModel
import com.since.run.presentaction.over_view.state_action.OverViewState
import com.since.run.presentaction.over_view.state_action.OverviewAction
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun RunOverViewScreenRoot(
    viewModel: OverViewViewModel = koinViewModel(),
    onActiveRunScreen : () -> Unit
) {

    val context  = LocalContext.current

    ObservableEvent(viewModel.channel) {event ->
        when(event){
            is OverViewEvent.Error -> {
                Toast.makeText(context, event.error.asUiText(context=context), Toast.LENGTH_SHORT).show()
            }
            OverViewEvent.Success -> {
                Toast.makeText(context, "Hai there!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    RunOverViewScreen(
        state = viewModel.state,
        action = {action ->
            when(action){
                OverviewAction.ActiveRunScreen -> {
                    onActiveRunScreen()
                }
            }
        }
    )

}

@Composable
fun RunOverViewScreen(
    state: OverViewState,
    action: (OverviewAction) -> Unit
) {


    RunsScaffold(
        topAppBar = {
            RunsTopAppBar(
                title = "Steps Tracker",
                trailingContent = {
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(30.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = Yellow,
                                    shape = CircleShape
                                )
                                .background(Yellow.copy(0.7f)),
                            contentAlignment = Alignment.Center
                        ) {

                            if(state.isLoading){
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(18.dp),
                                    strokeWidth = 2.dp,
                                    trackColor = MaterialTheme.colorScheme.background.copy(0.2f),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                            state.singleChar?.let {
                                Text(
                                    text = it.toString(),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }

                        }

                },
            )
        },
        floatingActionBtn = {
            RunsFabBtn(
                icon = RunIcon,
                color = Yellow,
                iconSize = 28.dp,
                onClick = {
                    action(OverviewAction.ActiveRunScreen)
                }
            )
        },
        snackBar = {  },
        isBackground = true,
        content = {

        }
    )

}

@Preview
@Composable
private fun RunOverViewScreenRoot() {
    StepsTrackerTheme {
        RunOverViewScreen(
            state = OverViewState(
                isLoading = true,
            ),
            action = {}
        )
    }
}