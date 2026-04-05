package com.since.presentaction.designsystem.component.run

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.since.presentaction.designsystem.ui.theme.LogoIcon
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.designsystem.ui.theme.Yellow

@Composable
fun RunsFabBtn(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    size : Dp=48.dp,
    color: Color,
    iconSize : Dp =18.dp,
    onClick : () -> Unit
    ) {


    Box(
        modifier=modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(color.copy(0.4f))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ){

        Box(modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color),
            contentAlignment = Alignment.Center){

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(iconSize)
            )

        }

    }

}


@Preview
@Composable
private fun RunsFabBtnPreview() {
    StepsTrackerTheme {
        RunsFabBtn(
            modifier = Modifier,
            icon = LogoIcon,
            color = Yellow,
            onClick = {}
        )
    }
}