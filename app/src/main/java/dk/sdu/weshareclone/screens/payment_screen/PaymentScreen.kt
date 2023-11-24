package dk.sdu.weshareclone.screens.payment_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun PaymentScreen(openAndPopUp: (String, String) -> Unit, viewModel: PaymentScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    PaymentContent(uiState = uiState, onAmountChange = viewModel::onAmountChange, onCreatePayment = viewModel::onCreatePayment)
}

@Composable
fun PaymentContent(
    uiState: PaymentUiState,
    onAmountChange: (String) -> Unit,
    onCreatePayment: () -> Unit
) {
    Column {
        Text(text = "Requested amount")
        TextField(value = uiState.amount, onValueChange = onAmountChange)
        Button(onClick = onCreatePayment, enabled = uiState.amount.isNotEmpty()) {
            Text(text = "Create payment")
        }
    }
}

