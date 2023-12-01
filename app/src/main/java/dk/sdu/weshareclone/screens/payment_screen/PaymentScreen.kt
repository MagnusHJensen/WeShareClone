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
    PaymentContent(uiState = uiState, onAmountChange = viewModel::onAmountChange, onCreatePayment = {viewModel.onCreatePayment(popUp)}, onReqUserChange = viewModel::onReqUserChange, onReqUserPercentChange = viewModel::onReqUserPercentChange)
}

@Composable
fun PaymentContent(
    uiState: PaymentUiState,
    onAmountChange: (String) -> Unit,
    onReqUserChange: (String) -> Unit,
    onReqUserPercentChange: (String) -> Unit,
    onCreatePayment: () -> Unit
) {
    Column {
        Text(text = "Requested amount")
        TextField(value = uiState.amount, onValueChange = onAmountChange, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        TextField(value = uiState.requestedUsers, onValueChange = onReqUserChange, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
        TextField(value = uiState.requestedUsersPercent, onValueChange = onReqUserPercentChange, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
        Button(onClick = onCreatePayment, enabled = uiState.amount != "0") {
            Text(text = "Create payment")
        }
    }
}

