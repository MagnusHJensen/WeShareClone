package dk.sdu.weshareclone.screens.profile_screen

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileService: ProfileService, private val accountService: AccountService) : WeShareViewModel() {
    var uiState = mutableStateOf(ProfileUiState())
        private set


    private val email
        get() = uiState.value.email

    private val pass
        get() = uiState.value.pass

    init {
        launchCatching {
        val currentUser = profileService.getProfile(accountService.currentUserId)
            uiState.value = uiState.value.copy(currentUser.name, currentUser.email)
             }
    }

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
}