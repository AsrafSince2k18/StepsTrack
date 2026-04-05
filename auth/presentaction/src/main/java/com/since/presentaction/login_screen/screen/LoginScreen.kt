package com.since.presentaction.login_screen.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.since.presentaction.designsystem.component.RunsBackground
import com.since.presentaction.designsystem.component.btn.RunsFullButton
import com.since.presentaction.designsystem.component.btn.RunsSegmentButton
import com.since.presentaction.designsystem.component.text_field.RunsPasswordTextField
import com.since.presentaction.designsystem.component.text_field.RunsTextField
import com.since.presentaction.designsystem.ui.theme.CheckIcon
import com.since.presentaction.designsystem.ui.theme.EmailIcon
import com.since.presentaction.designsystem.ui.theme.LockIcon
import com.since.presentaction.designsystem.ui.theme.StepsTrackerTheme
import com.since.presentaction.introscreen.screen.AppLogoContent
import com.since.presentaction.login_screen.event.LoginEvent
import com.since.presentaction.login_screen.state_action.LoginAction
import com.since.presentaction.login_screen.state_action.LoginState
import com.since.presentaction.login_screen.viewmodel.LoginViewmodel
import com.since.presentaction.ui.ui_error.ObservableEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewmodel : LoginViewmodel = koinViewModel(),
    onRegisterScreen:() -> Unit,
    onLoginSuccess :() -> Unit
) {


    val context = LocalContext.current
    val keyboardControl = LocalSoftwareKeyboardController.current

    ObservableEvent(
        viewmodel.channel
    ) { event->
        when(event){
            is LoginEvent.Error -> {
                keyboardControl?.hide()
                Toast.makeText(context, event.error.asUiText(context=context), Toast.LENGTH_LONG).show()
            }
            LoginEvent.Success -> {
                keyboardControl?.hide()
                Toast.makeText(context, "Login succeed", Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            }
        }
    }


    LoginScreen(
        state = viewmodel.state
    ) { action ->
        when(action){
            LoginAction.RegisterScreen -> {
                onRegisterScreen()
            }
            else -> Unit
        }
        viewmodel.onAction(action=action)
    }

}


@Composable
fun LoginScreen(
    state: LoginState,
    action:(LoginAction) -> Unit
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

            Text(text = "Welcome Back!",
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

            Text(text = "Log in to continue tracking your steps and progress.\n" +
                    "Don’t have an account? Create new one",
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
                btnClicks = 1,
                onBtn1Click = {
                    action(LoginAction.RegisterScreen)
                }
            )


            Spacer(
                modifier=Modifier
                    .height(12.dp)
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
                    action(LoginAction.PasswordToggle)
                }
            )


            RunsFullButton(
                hint = "Login",
                isEnable = state.btnEnable,
                isLoading = state.btnLoading,
                onClick = {
                    action(LoginAction.LoginBtn)
                }
            )

        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {

    StepsTrackerTheme {
        LoginScreen(
            state = LoginState()
        ) { }
    }
}