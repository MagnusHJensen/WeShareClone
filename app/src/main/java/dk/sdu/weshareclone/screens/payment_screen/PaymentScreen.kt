package dk.sdu.weshareclone.screens.payment_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun PaymentScreen(popUp: () -> Unit, viewModel: PaymentScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    PaymentContent(uiState = uiState, onAmountChange = viewModel::onAmountChange, onCreatePayment = {viewModel.onCreatePayment(popUp)})
}

@Composable
fun PaymentContent(
    uiState: PaymentUiState,
    onAmountChange: (String) -> Unit,
    onCreatePayment: () -> Unit
) {
    Column {
        Text(text = "Requested amount")
        TextField(value = uiState.amount.toString(), onValueChange = onAmountChange, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Button(onClick = onCreatePayment, enabled = uiState.amount > 0) {
            Text(text = "Create payment")
        }
    }
}

