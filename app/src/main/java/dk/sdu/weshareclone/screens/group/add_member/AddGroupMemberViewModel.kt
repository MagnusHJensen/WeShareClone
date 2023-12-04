package dk.sdu.weshareclone.screens.group.add_member

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.GROUP_ID
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class AddGroupMemberViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val groupService: GroupService
) :
    WeShareViewModel() {
    var uiState = mutableStateOf(AddGroupMemberUiState())
        private set

    val groupId = savedStateHandle.get<String>(GROUP_ID);

    fun onEmailChange(newEmail: String) {
        uiState.value = uiState.value.copy(email = newEmail)
    }

    fun onAddGroupMember(popUp: () -> Unit, onError: (String) -> Unit) {
        if (groupId == null) {
            onError("Something went wrong, please try again")
            return
        }

        if (uiState.value.email.isEmpty()) {
            onError("Email is empty")
            return
        }
        launchCatching(onError = onError) {
            groupService.inviteMember(groupId, uiState.value.email)
            popUp()
        }
    }
}