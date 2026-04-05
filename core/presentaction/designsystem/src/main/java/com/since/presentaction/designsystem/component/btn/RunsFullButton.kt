package com.since.presentaction.designsystem.component.btn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.since.presentaction.designsystem.ui.theme.RunsGray
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.designsystem.ui.theme.Yellow

@Composable
fun RunsFullButton(
    modifier: Modifier = Modifier,
    hint:String,
    isEnable: Boolean=false,
    isLoading: Boolean=false,
    onClick : () -> Unit) {


    Button(
        onClick = {
            onClick()
        },
        enabled = isEnable,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Yellow,
            contentColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = RunsGray
        ),
        modifier = modifier
            .height(IntrinsicSize.Max)
            .padding(12.dp)
    ) {
        Box(
            modifier=Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(text = hint,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .alpha(if(isLoading) 0f else 1f))


            CircularProgressIndicator(
                modifier = Modifier
                    .size(22.dp)
                    .alpha(if (isLoading) 1f else 0f),
                color = MaterialTheme.colorScheme.background,
                trackColor = RunsGray,
                strokeWidth = 2.dp
            )
        }


    }
}


@Preview
@Composable
private fun RunsButtonPreview() {
    StepsTrackerTheme {
        RunsFullButton(
            isEnable = true,
            isLoading = true,
            hint = "Login"
        ) { }
    }
}