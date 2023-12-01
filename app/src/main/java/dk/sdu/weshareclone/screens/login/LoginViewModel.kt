package dk.sdu.weshareclone.screens.login

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.LOGIN_SCREEN
import dk.sdu.weshareclone.PICK_NAME_SCREEN
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val accountService: AccountService, private val profileService: ProfileService) : WeShareViewModel() {
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
        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
        }
    }

    fun onCreateClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            accountService.createAccount(email, password)
            profileService.createProfile(email)
            openAndPopUp(PICK_NAME_SCREEN, LOGIN_SCREEN)
        }
    }
}