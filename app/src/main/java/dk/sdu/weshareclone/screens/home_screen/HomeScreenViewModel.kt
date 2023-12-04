package dk.sdu.weshareclone.screens.home_screen

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.GROUP_SCREEN
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.LOGIN_SCREEN
import dk.sdu.weshareclone.PROFILE_SCREEN
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

    val groups = mutableStateOf(emptyList<Group>())

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(LOGIN_SCREEN)
        }
    }

    fun fetchGroups() {
        launchCatching {
            groups.value = groupService.listGroups()
        }
    }

    fun onInspectProfile(openAndPopUp: (String, String) -> Unit) {
        launchCatching { openAndPopUp(PROFILE_SCREEN, HOME_SCREEN) }
    }

    fun onInspectGroup(group: Group, openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(GROUP_SCREEN, HOME_SCREEN)
    }
}