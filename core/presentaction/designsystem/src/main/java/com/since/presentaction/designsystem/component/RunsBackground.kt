package com.since.presentaction.designsystem.component

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.designsystem.ui.theme.Yellow

@Composable
fun RunsBackground(
    modifier: Modifier=Modifier,
    isToolBar: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    val screenWidthPx = with(density) {
        configuration.screenWidthDp.dp.roundToPx()
    }

    val smallDimension = min(
        a = configuration.screenHeightDp.dp,
        b = configuration.screenWidthDp.dp
    )

    val smallDimensionPx = with(density) {
        smallDimension.roundToPx()
    }

    val primaryColor = Yellow
    val android12 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (android12) Modifier.blur(smallDimension) else Modifier
                )
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            primaryColor,
                            primaryColor,
                            MaterialTheme.colorScheme.background
                        ),
                        center = Offset(
                            x = screenWidthPx / 5f,
                            y = 100f
                        ),
                        radius = smallDimensionPx / 2f
                    )
                )
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .then(if (!isToolBar) Modifier.systemBarsPadding() else Modifier)
        ) {
            content()
        }

    }


}

@Preview
@Composable
private fun RunsBackgroundPreview() {
    StepsTrackerTheme {
        RunsBackground() { }
    }
}