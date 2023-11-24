package dk.sdu.weshareclone.screens.payment_screen

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

    fun onAmountChange(setAmount: String) {
        try {
            uiState.value = uiState.value.copy(amount = setAmount)
        } catch (e: NumberFormatException) {
            return // Since the number inputted was invalid.
        }
    }

    fun onCreatePayment(popUp: () -> Unit) {
        launchCatching {
            paymentService.createPayment(requestedAmount)
            popUp()
        }
    }
}