package dk.sdu.weshareclone.screens.home_screen

import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.GROUP_SCREEN
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.LOGIN_SCREEN
import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    profileService: ProfileService,
    private val groupService: GroupService
) : WeShareViewModel() {

    val uiState = profileService.profile.map {
        if (it != null) HomeScreenUiState(it.name, isLoaded = true) else HomeScreenUiState()
    }

    var groups = emptyList<Group>()

    init {
        launchCatching {
            groups = groupService.listGroups()
        }
    }

    fun createGroup() {
        launchCatching {
            groupService.createGroup("Boys", "This is my boys", listOf(accountService.currentUserId))
        }
    }

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(LOGIN_SCREEN)
        }
    }
    fun onInspectGroup(group: Group, openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(GROUP_SCREEN, HOME_SCREEN)
    }
}