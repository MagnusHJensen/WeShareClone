package dk.sdu.weshareclone.screens.create_expense_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.components.InputField
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun CreateExpenseScreen(popUp: () -> Unit, viewModel: CreateExpenseViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    CreateExpenseContent(
        uiState = uiState,
        onAmountChange = viewModel::onAmountChange,
        onCheckPerson = viewModel::onCheckPerson,
        onCreateExpense = { viewModel.createExpense(popUp) })

}

@Composable
fun CreateExpenseContent(
    uiState: CreateExpenseUiState,
    onAmountChange: (String) -> Unit,
    onCheckPerson: (Profile) -> Unit,
    onCreateExpense: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "Create an expense for ${uiState.groupName}")
        InputField(label = "Total amount", value = uiState.amount, onValueChange = onAmountChange)
        Column {
            uiState.groupMembers.forEach { profile ->
                Row(verticalAlignment = CenterVertically) {
                    Checkbox(checked = uiState.includedPeople.contains(profile.id), onCheckedChange = { onCheckPerson(profile) })
                    Text(text = profile.name)
                }
            }
        }
        Button(onClick = onCreateExpense) {
            Text(text = "Create expense")
        }
    }
}

@Composable
@Preview
fun PreviewCreateExpense() {
    val peopleSplit = mutableMapOf<Profile, Boolean>();
    peopleSplit[Profile("123", "Boy A", "123@123.123")] = false
    val uiState = CreateExpenseUiState(
        amount = "0",
        groupName = "Boys",
        includedPeople = mutableListOf()
    )
    WeShareTheme {
        CreateExpenseContent(uiState, {}, {}, {})
    }
}