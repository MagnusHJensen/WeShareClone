package dk.sdu.weshareclone.screens.group.create_group

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(private val groupService: GroupService, private val accountService: AccountService) :
    WeShareViewModel() {
    var uiState = mutableStateOf(CreateGroupUiState())
        private set

    fun onNameChange(newName: String) {
        uiState.value = uiState.value.copy(name = newName)
    }

    fun onDescriptionChange(newDescription: String) {
        uiState.value = uiState.value.copy(description = newDescription)
    }

    fun onCreateGroupClick(popUp: () -> Unit) {
        launchCatching {
            groupService.createGroup(uiState.value.name, uiState.value.description, listOf(accountService.currentUserId))
            popUp()
        }
    }
}