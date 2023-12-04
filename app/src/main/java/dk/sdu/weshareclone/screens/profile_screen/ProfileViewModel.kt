package dk.sdu.weshareclone.screens.profile_screen

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileService: ProfileService) : WeShareViewModel() {
    var uiState = mutableStateOf(ProfileUiState())
        private set

    private val name
        get() = uiState.value.name

    private val email
        get() = uiState.value.email

    private val pass
        get() = uiState.value.pass

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
}