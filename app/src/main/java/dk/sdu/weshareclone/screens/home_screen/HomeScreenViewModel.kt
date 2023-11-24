package dk.sdu.weshareclone.screens.home_screen

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.LOGIN_SCREEN
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.PaymentService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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

    var ownedPayments: Flow<List<PaymentViewModel>> = paymentService.ownedPayments.flatMapLatest {list ->

        val paymentViewModel: MutableList<PaymentViewModel> = mutableListOf();

        list.forEach {
            val profile = profileService.getProfile(it.owner) // Fetch owner profile.
            Log.d("APP", "Mapping payment " + it.id)
            if (profile != null) {
                paymentViewModel.add(PaymentViewModel(it, profile))
            } else {
                Log.e("APP", "Skipped payment " + it.id + " since attached owner profile is null")
            }
        }

        flowOf(paymentViewModel)
    }

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(LOGIN_SCREEN)
        }
    }
}