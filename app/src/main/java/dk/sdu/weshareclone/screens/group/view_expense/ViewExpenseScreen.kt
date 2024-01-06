package dk.sdu.weshareclone.screens.group.view_expense

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun ViewExpenseScreen(viewModel: ViewExpenseViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    ViewExpenseScreenContent(uiState = uiState, sendNotification = viewModel::sendNotification, payExpense = viewModel::payExpense)
}

@Composable
fun ViewExpenseScreenContent(
    uiState: ViewExpenseUiState,
    sendNotification: (String) -> Unit,
    payExpense: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(0.dp, 8.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        ExpenseCard(uiState, sendNotification, payExpense)
    }
}

@Composable
fun ExpenseCard(
    uiState: ViewExpenseUiState,
    sendNotification: (String) -> Unit,
    payExpense: () -> Unit
) {
    val context = LocalContext.current

    Card(
        backgroundColor = Color.Green, modifier = Modifier
            .fillMaxWidth(0.9F)
            .fillMaxHeight(0.75F)
    ) {
        Column(
            modifier = Modifier.padding(16.dp, 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(text = uiState.creator?.name ?: "Unknown")
                Row {
                    Text(text = "Total bill: ", fontWeight = FontWeight.Bold)
                    Text(text = uiState.total.toString())
                }
                Text(text = uiState.reason)
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp, 16.dp)
                        .weight(weight = 1f, fill = false)
                ) {
                    item {
                        Text(text = "Members", fontWeight = FontWeight.Bold)
                    }
                    items(uiState.peopleSplit.keys.toList(), key = { it.id }) {
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = it.name)
                                if (uiState.isOwner && it.id != uiState.creator?.id && uiState.peopleSplit[it] == false) { // Don't show on owner or people that have already paid.
                                    IconButton(onClick = {
                                        sendNotification(it.notificationToken)
                                        Toast.makeText(
                                            context,
                                            "Sent a reminder",
                                            Toast.LENGTH_SHORT
                                        )
                                    }) {
                                        Icon(Icons.Rounded.Notifications, contentDescription = null)
                                    }
                                }
                            }
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = uiState.equalSplit.toString())
                                if (uiState.peopleSplit[it] == true) {
                                    Icon(Icons.Rounded.Check, contentDescription = null)
                                } else {
                                    Icon(Icons.Rounded.Close, contentDescription = null)
                                }
                            }
                        }
                        Divider(color = Color.Black, thickness = 2.dp)
                    }
                }
            }
            if (!uiState.isPaid) {
                if (uiState.isPaying) {
                    CircularProgressIndicator()
                } else {
                    Button(onClick = payExpense) {
                        Text(text = "Pay")
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ViewExpenseScreenPreview() {
    WeShareTheme {
        ViewExpenseScreenContent(uiState = ViewExpenseUiState(), sendNotification = {}, payExpense = {})
    }
}