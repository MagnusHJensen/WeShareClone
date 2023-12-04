package dk.sdu.weshareclone.screens.group.group_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import dk.sdu.weshareclone.screens.group.group_details.GroupDetailsScreenUiState

@Composable
fun BalanceSection(uiState: GroupDetailsScreenUiState) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        if (uiState.isBalanceInCheck()) {
            BalanceInCheck()
        } else {
            BalanceColumn(label = "Money Owed", amount = uiState.amountOwed)
            BalanceColumn(label = "Money Ows", amount = uiState.amountOws)

        }
    }
}   

@Composable
fun BalanceInCheck() {
    Text(text = "You are all caught up with your expenses")
}

@Composable
fun BalanceColumn(label: String, amount: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label)
        Text(text = amount.toString())
    }
}