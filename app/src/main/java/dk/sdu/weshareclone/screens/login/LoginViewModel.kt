package dk.sdu.weshareclone.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.LOGIN_SCREEN
import dk.sdu.weshareclone.PICK_NAME_SCREEN
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.screens.WeShareViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val accountService: AccountService) : WeShareViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser) openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        /*if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }*/

        Log.d("APP", "Sign in clicked")
        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
        }
    }

    fun onCreateClick(openAndPopUp: (String, String) -> Unit) {
        /*if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }*/

        launchCatching {
            accountService.createAccount(email, password)
            openAndPopUp(PICK_NAME_SCREEN, LOGIN_SCREEN)
        }
    }
}