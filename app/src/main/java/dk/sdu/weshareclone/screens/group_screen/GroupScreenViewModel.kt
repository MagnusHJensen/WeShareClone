package dk.sdu.weshareclone.screens.group_screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.EmptyPath
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import dk.sdu.weshareclone.screens.home_screen.HomeScreenUiState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class GroupScreenViewModel @Inject constructor(
    private val groupService: GroupService,
    private val profileService: ProfileService,
): WeShareViewModel() {
    val uiState = mutableStateOf(Group());

    fun fetchGroupId(groupId: String) {
        launchCatching {
            uiState.value = groupService.fetchGroup(groupId)
        }
    }
}