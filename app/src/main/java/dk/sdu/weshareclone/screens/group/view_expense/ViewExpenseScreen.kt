package dk.sdu.weshareclone.screens.group.view_expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun ViewExpenseScreen(viewModel: ViewExpenseViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    ViewExpenseScreenContent(uiState = uiState, sendNotification = viewModel::sendNotification)
}

@Composable
fun ViewExpenseScreenContent(
    uiState: ViewExpenseUiState,
    sendNotification: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = uiState.reason)
        Text(text = "Total amount: ${uiState.total}")
        LazyColumn {
            items(uiState.peopleSplit.keys.toList(), key = { it.id }) {
                Row {
                    Text(text = it.name)
                    Text(text = "Amount to pay: ${uiState.equalSplit}") // Will be the same for everyone
                    IconButton(onClick = { sendNotification(it.notificationToken) }) {
                        Icon(Icons.Filled.Notifications, contentDescription = null)
                    }
                    if (false) {
                        Checkbox(checked = true, onCheckedChange = null, enabled = false) // Will just be checked, but can not be interacted with.
                    }
                }
            }
        }
        if (!false) { // If has paid
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Pay")
            }
        }
    }
}


@Composable
@Preview
fun ViewExpenseScreenPreview() {
    WeShareTheme {
        ViewExpenseScreenContent(uiState = ViewExpenseUiState(), sendNotification = {})
    }
}