package dk.sdu.weshareclone.screens.home_screen

import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.LOGIN_SCREEN
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.PaymentService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    private val profileService: ProfileService,
    private val paymentService: PaymentService
) : WeShareViewModel() {

    val uiState = profileService.profile.map {
        if (it != null) HomeScreenUiState(it.name, isLoaded = true) else HomeScreenUiState()
    }

    var ownedPayments = paymentService.ownedPayments

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(LOGIN_SCREEN)
        }
    }
}