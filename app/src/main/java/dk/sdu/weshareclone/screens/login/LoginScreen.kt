package dk.sdu.weshareclone.screens.login

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun LoginSceen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onAppStart = { viewModel.onAppStart(openAndPopUp) },
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = {
            viewModel.onSignInClick(openAndPopUp)
        },
        onCreateClick = { viewModel.onCreateClick(openAndPopUp) },
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onAppStart: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onCreateClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(uiState.email, onEmailChange)
        PasswordInput(uiState.password, onPasswordChange)
        Row {
            Button(
                onClick = { onSignInClick() },
                enabled = uiState.email.isNotEmpty() && uiState.password.isNotEmpty()
            ) {
                Text("Sign in")
            }
        }
        Row {
            Button(
                onClick = { onCreateClick() },
                enabled = uiState.email.isNotEmpty() && uiState.password.isNotEmpty()
            ) {
                Text("Create account")
            }
        }
    }

    LaunchedEffect(true) {
        onAppStart()
    }
}

@Composable
fun EmailInput(email: String, onEmailChange: (String) -> Unit) {
    Column {
        Text(text = "Email")
        TextField(
            singleLine = true,
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.border(border = BorderStroke(width = Dp(1.0F), color = Color.Black))
        )
    }
}


@Composable
fun PasswordInput(password: String, onPasswordChange: (String) -> Unit) {
    var isError by remember { mutableStateOf(false) }
    val minCharLimit = 6
    fun validate(text: String) {
        isError = text.length < minCharLimit
    }

    Column {
        Text(text = "Password")
        TextField(
            value = password,
            isError = isError,
            onValueChange = {
                onPasswordChange(it)
                validate(password)
            },
            singleLine = true,
            modifier = Modifier.border(border = BorderStroke(width = Dp(1.0F), color = Color.Black))
        )
    }
}
