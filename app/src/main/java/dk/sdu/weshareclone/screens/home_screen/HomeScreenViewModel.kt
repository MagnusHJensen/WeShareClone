package dk.sdu.weshareclone.screens.home_screen

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.LOGIN_SCREEN
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    private val profileService: ProfileService
) : WeShareViewModel() {

    val uiState = profileService.profile.map {
        if (it != null) HomeScreenUiState(it.name, isLoaded = true) else HomeScreenUiState()
    }

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(LOGIN_SCREEN)
        }
    }

    fun updateName() {
        launchCatching {
            profileService.updateName((Random.Default.nextFloat() * 100).toString())
        }
    }
}