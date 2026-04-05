package com.since.run.presentaction.active_run.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.since.presentaction.designsystem.component.run.RunsFabBtn
import com.since.presentaction.designsystem.ui.theme.PauseIcon
import com.since.presentaction.designsystem.ui.theme.StartIcon
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.designsystem.ui.theme.Yellow
import com.since.presentaction.ui.run.utlity.formatedKm
import com.since.presentaction.ui.run.utlity.paceCalculate
import com.since.presentaction.ui.run.utlity.timeFormat
import com.since.presentaction.ui.run.utlity.toTimeFormat
import com.since.run.domain.activerun_screen.RunData
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunsBottomScaffold(
    time:Long,
    elapsedTime: Duration,
    runData: RunData,
    isTracking: Boolean,
    onStart : () -> Unit
) {


    Column(
        modifier = Modifier
            .padding(top = 62.dp)
            .padding(8.dp)
            .padding(bottom = 12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        RunDataValue(
            title = "Duration",
            value = elapsedTime.toTimeFormat(),
            valueSize = 26.sp
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            RunDataValue(
                title = "Time",
                value = time.timeFormat(),
            )


            RunDataValue(
                title = "Distance",
                value = (runData.distanceMeter/1000.0).formatedKm(),
            )

            RunDataValue(
                title = "Pace",
                value = (runData.pace.paceCalculate(
                    runData.distanceMeter/1000.0))
            )


        }

        Spacer(
            modifier = Modifier
                .height(22.dp)
        )

        Row(
            modifier=Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RunsFabBtn(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Yellow,
                        shape = CircleShape
                    ),
                icon = if(isTracking) PauseIcon else StartIcon,
                color = Yellow,
                onClick = {
                    onStart()
                }
            )


        }

    }


}


@Composable
fun RunDataValue(
    title: String,
    value: String,
    valueSize: TextUnit = 18.sp
) {


    Column(
        modifier = Modifier
            .defaultMinSize(minWidth = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
                    .copy(0.7f)
            )
        )

        Spacer(
            modifier = Modifier
                .height(4.dp)
        )


        Text(
            text = value,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = valueSize,
                color = MaterialTheme.colorScheme.onBackground
            ),
            overflow = TextOverflow.Ellipsis
        )

    }

}


@Preview
@Composable
private fun RunsScaffoldPreview() {
    StepsTrackerTheme {
        RunsBottomScaffold(
            time = System.currentTimeMillis(),
            elapsedTime = 10.minutes,
            runData = RunData(
                distanceMeter = 5674,
                pace = 10.minutes
            ),
            isTracking = true,
            onStart = {}
        )
    }
}