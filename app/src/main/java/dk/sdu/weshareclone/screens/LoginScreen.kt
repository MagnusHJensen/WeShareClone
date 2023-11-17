@file:OptIn(ExperimentalMaterial3Api::class)

package dk.sdu.weshareclone.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import dk.sdu.weshareclone.ViewModel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginSceen(navigation: NavController, profile: ProfileViewModel = koinViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Log.d("APP", profile.profile.toString())

    fun wrapCreateAccount(email: String, password: String) {
        try {
            //createAccount(email, password)
        } catch (e: FirebaseAuthException) {
            if (e is FirebaseAuthEmailException) {
                Log.d("APP", "Bad email")
            }
        }
    }


    Column {
        EmailInput(email, onEmailChange = { email = it })
        PasswordInput(password, onPasswordChange = { password = it })
        Row {
            Button(
                onClick = { wrapCreateAccount(email, password) },
                enabled = email.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Create account")
            }
            Button(
                onClick = { Log.d("APP", "signIn") },
                enabled = email.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Sign in")
            }
        }
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
        TextField(value = password, isError = isError, onValueChange = {
            onPasswordChange(it)
            validate(password)
        }, singleLine = true, supportingText = {
            if (isError) {
                Text("Password must be minimum 6 characters long.")
            }
        }, trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
        }, modifier = Modifier.border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
    }
}
