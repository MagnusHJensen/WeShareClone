package dk.sdu.weshareclone.screens.pick_name

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.PICK_NAME_SCREEN
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class PickNameViewModel @Inject constructor(private val profileService: ProfileService) : WeShareViewModel() {
    var uiState = mutableStateOf(PickNameUiState())
        private set

    private val name
        get() = uiState.value.name

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onPickName(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            profileService.updateName(name)
            openAndPopUp(HOME_SCREEN, PICK_NAME_SCREEN)
        }
    }
}