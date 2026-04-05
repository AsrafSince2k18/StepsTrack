package com.since.presentaction.designsystem.component.text_field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.since.presentaction.designsystem.ui.theme.CheckIcon
import com.since.presentaction.designsystem.ui.theme.EmailIcon
import com.since.presentaction.designsystem.ui.theme.RunsGray
import com.since.presentaction.designsystem.ui.theme.RunsGray40
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme

@Composable
fun RunsTextField(
    state: TextFieldState,
    startTitle:String,
    endTitle:String?,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector?,
    placeholder:String,
    keyboardType: KeyboardType= KeyboardType.Text
) {

    var isFocus by rememberSaveable {
        mutableStateOf(false)
    }



    Column(
        modifier=Modifier
            .padding(4.dp)
    ) {
        Row(
            modifier=Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = startTitle,
                color = RunsGray,
                fontSize = 14.sp
            )

            endTitle?.let {
                Text(text = it,
                    color = RunsGray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(
            modifier=Modifier
                .height(4.dp)
        )

        BasicTextField(
            state = state,
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = keyboardType
            ),
            modifier = Modifier
                .onFocusChanged{
                    isFocus=it.isFocused
                }
                .padding(horizontal = 4.dp)
                .background(
                    color = if(isFocus) MaterialTheme.colorScheme.primary.copy(0.02f) else RunsGray40,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = if(isFocus) MaterialTheme.colorScheme.primary else Color.Unspecified,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp)),
            decorator = {decorator->

                Row(
                    modifier=Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = RunsGray,
                        modifier = Modifier
                            .padding(end = 4.dp)
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ){

                        if(state.text.isEmpty()){
                            Text(text = placeholder,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.4f),
                                fontSize = 16.sp)
                        }
                        decorator()

                    }

                    trailingIcon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = RunsGray,
                            modifier = Modifier
                                .padding(start = 4.dp)

                        )
                    }


                }

            }
        )

    }

}

@Preview
@Composable
private fun RunsTextFieldPreview() {
    StepsTrackerTheme {
        RunsTextField(
            state = TextFieldState(),
            startTitle = "Email",
            endTitle = "Must valid email address",
            leadingIcon = EmailIcon,
            trailingIcon = CheckIcon,
            placeholder = "example@gmail.com",
        )
    }
}