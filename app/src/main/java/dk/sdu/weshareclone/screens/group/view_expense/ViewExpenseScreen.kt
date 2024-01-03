package dk.sdu.weshareclone.screens.group.view_expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun ViewExpenseScreen(viewModel: ViewExpenseViewModel = hiltViewModel()) {

}

@Composable
fun ViewExpenseScreenContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Reason for expense")
        Text(text = "Total amount: ${100}")
        LazyColumn {
            items(listOf(1, 2, 3), key = { it }) {
                Row {
                    Text(text = "Member name")
                    Text(text = "Amount") // Will be the same for everyone
                    Checkbox(checked = true, onCheckedChange = null, enabled = false) // Will just be checked, but can not be interacted with.
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
        ViewExpenseScreenContent()
    }
}