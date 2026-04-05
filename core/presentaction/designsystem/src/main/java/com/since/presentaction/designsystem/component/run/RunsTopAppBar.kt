package com.since.presentaction.designsystem.component.run

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.since.presentaction.designsystem.ui.theme.ArrowLeftIcon
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunsTopAppBar(
    modifier: Modifier=Modifier,
    title:String?=null,
    trailingContent: (@Composable () -> Unit)? = null,
    backBtn: Boolean = false,
    onBackClick: () -> Unit = {},

    ) {


    TopAppBar(
        modifier=modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,

        ),
        title = {
            title?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 22.sp
                    )
                )
            }
        },
        navigationIcon = {
            if (backBtn) {
                IconButton(
                    onClick = {
                        onBackClick()
                    }
                ) {
                    Icon(
                        imageVector = ArrowLeftIcon,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            if (trailingContent != null) {
                trailingContent()
            }
        }
    )

}

@Preview
@Composable
private fun RunsTopAppBarPreview() {
    StepsTrackerTheme {

    }
}