package dk.sdu.weshareclone.screens.home_screen

import dk.sdu.weshareclone.model.Payment
import dk.sdu.weshareclone.model.Profile

data class PaymentViewModel(
    val payment: Payment,
    val owner: Profile
)