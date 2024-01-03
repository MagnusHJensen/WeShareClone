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
        onReasonChange = viewModel::onReasonChange,
        onCheckPerson = viewModel::onCheckPerson,
        onCreateExpense = { viewModel.createExpense(popUp) })

}

@Composable
fun CreateExpenseContent(
    uiState: CreateExpenseUiState,
    onAmountChange: (String) -> Unit,
    onReasonChange: (String) -> Unit,
    onCheckPerson: (Profile) -> Unit,
    onCreateExpense: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "Create an expense for ${uiState.groupName}")
        InputField(label = "Reason for expense", value = uiState.reason, onValueChange = onReasonChange)
        InputField(label = "Total amount", value = uiState.amount, onValueChange = onAmountChange)
        Column {
            Text(text = "Check the members that are part of the expense")
            uiState.groupMembers.forEach { profile ->
                Row(verticalAlignment = CenterVertically) {
                    Checkbox(
                        checked = uiState.includedPeople.contains(profile.id),
                        onCheckedChange = { onCheckPerson(profile) })
                    Text(text = profile.name)
                }
            }
        }
        Button(
            onClick = onCreateExpense,
            enabled = uiState.includedPeople.size > 0 && uiState.amount.toInt() > 0
        ) {
            Text(text = "Create expense")
        }
    }
}

@Composable
@Preview
fun PreviewCreateExpenseEnabled() {
    val uiState = generatePreviewUiState(includedPeople = mutableListOf("1234"))
    WeShareTheme {
        CreateExpenseContent(uiState, {}, {}, {}, {})
    }
}

@Composable
@Preview
fun PreviewCreateExpenseMissingPrice() {
    val uiState = generatePreviewUiState(amount = "0", includedPeople = mutableListOf("1234"))
    WeShareTheme {
        CreateExpenseContent(uiState, {}, {}, {}, {})
    }
}

@Composable
@Preview
fun PreviewCreateExpenseMissingMemberSelection() {
    val uiState = generatePreviewUiState()
    WeShareTheme {
        CreateExpenseContent(uiState, {}, {}, {}, {})
    }
}

fun generatePreviewUiState(
    reason: String = "Shared expense",
    amount: String = "10",
    groupName: String = "My group",
    includedPeople: MutableList<String> = mutableListOf(),
    groupMembers: List<Profile> = listOf(
        Profile("123", "Test Member", "test@gmail.com"),
        Profile("1234", "Test Member 2", "test2@gmail.com")
    )
): CreateExpenseUiState {
    return CreateExpenseUiState(reason, amount, groupName, groupMembers, includedPeople)
}