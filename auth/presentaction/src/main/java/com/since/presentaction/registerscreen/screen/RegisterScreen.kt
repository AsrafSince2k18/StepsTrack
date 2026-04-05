package com.since.presentaction.registerscreen.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.since.auth.domain.user_validator.PasswordValidate
import com.since.auth.domain.user_validator.UserDataValidate.Companion.PASSWORD_LENGTH
import com.since.presentaction.designsystem.component.RunsBackground
import com.since.presentaction.designsystem.component.btn.RunsFullButton
import com.since.presentaction.designsystem.component.btn.RunsSegmentButton
import com.since.presentaction.designsystem.component.text_field.RunsPasswordTextField
import com.since.presentaction.designsystem.component.text_field.RunsTextField
import com.since.presentaction.designsystem.ui.theme.CheckIcon
import com.since.presentaction.designsystem.ui.theme.EmailIcon
import com.since.presentaction.designsystem.ui.theme.LockIcon
import com.since.presentaction.designsystem.ui.theme.PersonIcon
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.introscreen.screen.AppLogoContent
import com.since.presentaction.registerscreen.event.RegisterEvent
import com.since.presentaction.registerscreen.state_action.RegisterAction
import com.since.presentaction.registerscreen.state_action.RegisterState
import com.since.presentaction.registerscreen.viewmodel.RegisterViewmodel
import com.since.presentaction.ui.R
import com.since.presentaction.ui.ui_error.ObservableEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewmodel: RegisterViewmodel = koinViewModel(),
    onRegisterSuccess : () -> Unit,
    onLoginScreen : () -> Unit
) {

    val context = LocalContext.current
    val keyboardControl = LocalSoftwareKeyboardController.current

    ObservableEvent(viewmodel.channel) {event->
        when(event){
            is RegisterEvent.Error -> {
                keyboardControl?.hide()
                Toast.makeText(context, event.error.asUiText(context=context), Toast.LENGTH_LONG).show()
            }
            RegisterEvent.Success -> {
                keyboardControl?.hide()
                Toast.makeText(context, "Register success", Toast.LENGTH_SHORT).show()
                onRegisterSuccess()
            }
        }
    }



    RegisterScreen(
        state = viewmodel.state,
        action = {action->
            when(action){
                RegisterAction.LoginScreen -> {
                    onLoginScreen()
                }
                else -> Unit
            }
            viewmodel.onAction(action=action)
        }
    )

}


@Composable
fun RegisterScreen(
    state: RegisterState,
    action:(RegisterAction) -> Unit
) {


    val scrollState = rememberScrollState()

    RunsBackground{
        Column(
            modifier=Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(scrollState)
                .padding(12.dp)
                .padding(top = 80.dp)
        ) {
            AppLogoContent()

            Spacer(
                modifier=Modifier
                    .height(18.dp)
            )

            Text(text = "Welcome to Steps Tracker",
                style= TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )


            Spacer(
                modifier=Modifier
                    .height(12.dp)
            )

            Text(text = " Register with your email to access all StepsTracker features.\n" +
                    "Already have an account? Log in to continue.",
                style= TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )

            Spacer(
                modifier=Modifier
                    .height(22.dp)
            )


            RunsSegmentButton(
                btn1Text = "Register",
                btn2Text = "Login",
                onBtn2Click = {
                    action(RegisterAction.LoginScreen)
                }
            )


            Spacer(
                modifier=Modifier
                    .height(12.dp)
            )

            RunsTextField(
                state = state.name,
                startTitle = "Name",
                endTitle = null,
                leadingIcon = PersonIcon,
                trailingIcon = null,
                placeholder = "Enter your Full Name"
            )

            RunsTextField(
                state = state.email,
                startTitle = "Email",
                endTitle = if(!state.emailValid) "Must valid email address" else null,
                leadingIcon = EmailIcon,
                trailingIcon = if(state.emailValid) CheckIcon else null,
                placeholder = "Enter your Email",
                keyboardType = KeyboardType.Email
            )

            RunsPasswordTextField(
                state = state.password,
                startTitle = "Password",
                leadingIcon = LockIcon,
                placeholder = "Enter your Password",
                passwordVisible = state.passwordVisible,
                keyboardType = KeyboardType.Password,
                onPasswordVisible = {
                    action(RegisterAction.PasswordToggle)
                }
            )

            Spacer(
                modifier=Modifier
                    .height(12.dp)
            )
            PasswordValidator(
                isValid = state.passwordValidate.isLength,
                value = stringResource(R.string.password_legth,PASSWORD_LENGTH)
            )

            Spacer(
                modifier= Modifier
                    .height(4.dp)
            )

            PasswordValidator(
                isValid = state.passwordValidate.isNumber,
                value = "At least one number"
            )

            Spacer(
                modifier= Modifier
                    .height(4.dp)
            )

            PasswordValidator(
                isValid = state.passwordValidate.isLowerCase,
                value = "Contains lowercase characters"
            )

            Spacer(
                modifier= Modifier
                    .height(4.dp)
            )

            PasswordValidator(
                isValid = state.passwordValidate.isUpperCase,
                value = "Contains uppercase characters"
            )

            Spacer(
                modifier= Modifier
                    .height(4.dp)
            )

            RunsFullButton(
                hint = "Register",
                isEnable = state.btnEnable,
                isLoading = state.btnLoading,
                onClick = {
                    action(RegisterAction.RegisterBtn)
                }
            )

        }
    }
}


@Composable
private fun PasswordValidator(
    isValid : Boolean,
    value:String
) {


    Row(
        modifier= Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier=Modifier
                .size(15.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onBackground
                )
                .background(if (isValid) MaterialTheme.colorScheme.onBackground else Color.Unspecified),
            contentAlignment = Alignment.Center
        ){

            if(isValid){
                Icon(
                    imageVector = CheckIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .padding(1.dp)
                )
            }
        }


        Text(
            text = value,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Preview
@Composable
private fun RegisterScreenRootPreview() {
    StepsTrackerTheme{
        RegisterScreen(
            state = RegisterState(
                passwordValidate = PasswordValidate(
                    isNumber = true
                )
            ),
            action = {}
        )
    }
}