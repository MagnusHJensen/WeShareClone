package dk.sdu.weshareclone.screens.payment_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.model.service.impl.PaymentServiceImpl
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentScreenViewModel @Inject constructor(private val paymentService: PaymentServiceImpl) :
    WeShareViewModel() {
    var uiState = mutableStateOf(PaymentUiState())
        private set

    private val requestedAmount
        get() = uiState.value.amount
    private val requestedUsers
        get() = uiState.value.requestedUsers
    private val requestedUsersPercent
        get() = uiState.value.requestedUsersPercent

    fun onAmountChange(setAmount: String) {
        try {
            uiState.value = uiState.value.copy(amount = setAmount)
        } catch (e: NumberFormatException) {
            return // Since the number inputted was invalid.
        }
    }

    fun onReqUserChange(requestedUsers: String) {
        try {
            uiState.value = uiState.value.copy(requestedUsers = requestedUsers)
        } catch (e: Exception) {

        }
    }

    fun onReqUserPercentChange(requestedUsersPercent: String) {
        try {
            uiState.value = uiState.value.copy(requestedUsersPercent = requestedUsersPercent)
        } catch (e: Exception) {

        }
    }

    fun onCreatePayment(popUp: () -> Unit) {
        launchCatching {
            val requestedUserMap = HashMap<String, Int>()
            requestedUserMap[requestedUsers] = requestedUsersPercent.toInt()
            Log.d("PAYMENT", "This is payment is created for $requestedUsers and they have to pay $requestedUsersPercent%")
            paymentService.createPayment(requestedAmount, requestedUserMap)
            popUp()
        }
    }
}