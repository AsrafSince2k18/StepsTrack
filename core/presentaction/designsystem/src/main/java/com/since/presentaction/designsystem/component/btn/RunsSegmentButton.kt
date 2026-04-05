package com.since.presentaction.designsystem.component.btn

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.since.presentaction.designsystem.ui.theme.RunsGray
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.designsystem.ui.theme.Yellow

@Composable
fun RunsSegmentButton(
    shape: Dp =12.dp,
    btn1Text:String,
    btn2Text:String,
    btnClicks : Int=0,
    onBtn1Click :() -> Unit ={},
    onBtn2Click :() -> Unit ={},) {


    var btnClick by rememberSaveable {
        mutableIntStateOf(0)
    }
    LaunchedEffect(btnClicks) {
        btnClick=btnClicks
    }



    MultiChoiceSegmentedButtonRow(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        space = 4.dp
    ) {
        SegmentedButton(
            checked = btnClick == 0,
            onCheckedChange = {
                onBtn1Click()
            },
            shape = RoundedCornerShape(shape),
            label = {
                Text(text = btn1Text,
                    fontSize = 18.sp,
                    color = if(btnClick!=0) RunsGray else MaterialTheme.colorScheme.background)
            },
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = Yellow,
                activeContentColor = MaterialTheme.colorScheme.background,
                inactiveContainerColor = Color.Transparent,
                inactiveContentColor = RunsGray,
                activeBorderColor = Color.Transparent,
                disabledActiveContentColor = RunsGray
            ), icon ={},
            modifier = Modifier
                .weight(1f)

        )

        SegmentedButton(
            checked = btnClick==1,
            onCheckedChange = {
                onBtn2Click()
            },
            shape = RoundedCornerShape(shape),
            label = {
                Text(text = btn2Text,
                    fontSize = 18.sp,
                    color = if(btnClick!=1) RunsGray else MaterialTheme.colorScheme.background)
            },
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = Yellow,
                activeContentColor = MaterialTheme.colorScheme.background,
                inactiveContainerColor = Color.Transparent,
                inactiveContentColor = RunsGray,
                activeBorderColor = Color.Transparent,
                disabledActiveContentColor = RunsGray
            ), icon ={},
            modifier = Modifier
                .weight(1f)
        )

    }

}


@Preview()
@Composable
fun RunsSegmentButtonPreview(modifier: Modifier = Modifier) {
    StepsTrackerTheme {
            RunsSegmentButton(
                btn1Text = "Login",
                btn2Text = "SignUp",
                onBtn1Click = {}
            ) { }

    }
}