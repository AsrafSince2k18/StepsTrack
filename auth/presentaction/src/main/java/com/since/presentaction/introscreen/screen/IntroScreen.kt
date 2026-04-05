package com.since.presentaction.introscreen.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.since.presentaction.designsystem.component.RunsBackground
import com.since.presentaction.designsystem.component.btn.RunsFullButton
import com.since.presentaction.designsystem.component.btn.RunsOutlineButton
import com.since.presentaction.designsystem.ui.theme.LogoIcon
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.introscreen.state_action.IntroAction

@Composable
fun IntroScreenRoot(
    onRegister:() -> Unit,
    onLogin:() -> Unit
) {

    IntroScreen {action ->
        when(action){
            IntroAction.LogIn -> {
                onLogin()
            }
            IntroAction.Register -> {
                onRegister()
            }
        }
    }

}


@Composable
fun IntroScreen(
    action:(IntroAction) -> Unit
) {

    RunsBackground(
        modifier=Modifier
            .padding(12.dp)
    ){
        Column(
            modifier =Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogoContent(
                modifier=Modifier
                    .padding(top=80.dp)
            )

            Spacer(
                modifier=Modifier
                    .height(6.dp)
            )

            Text(
                text = "Track Every Step\n" +
                        "Stay active every day",
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

        }

        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            RunsFullButton(
                modifier = Modifier
                    .weight(1f),
                hint = "Register",
                isEnable = true,
                isLoading = false,
                onClick = {
                    action(IntroAction.Register)
                }
            )


            RunsOutlineButton(
                modifier = Modifier
                    .weight(1f),
                hint = "LogIn",
                isEnable = true,
                isLoading = false,
                onClick = {
                    action(IntroAction.LogIn)
                }
            )
        }

    }

}


@Composable
fun AppLogoContent(
    modifier: Modifier=Modifier
) {

    Row  (
        modifier=modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 28.sp,
                )
            ){
                append("Steps\nTracke")
            }

            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 28.sp,
                )
            ){
                append("r")
            }
        }

        Text(text = annotatedText)

        Spacer(modifier=Modifier
            .width(4.dp))
        Icon(
            imageVector = LogoIcon,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(70.dp)
        )
    }

}

@Preview
@Composable
private fun IntroScreenPreview() {
    StepsTrackerTheme {
        IntroScreen({

        })
    }
}