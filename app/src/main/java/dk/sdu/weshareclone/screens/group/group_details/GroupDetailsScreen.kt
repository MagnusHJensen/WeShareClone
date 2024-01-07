package dk.sdu.weshareclone.screens.group.group_details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.ADD_GROUP_MEMBER_SCREEN
import dk.sdu.weshareclone.GROUP_ID
import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.screens.group.group_details.components.BalanceSection
import dk.sdu.weshareclone.screens.group.group_details.models.GroupExpense
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun GroupScreen(
    popUp: () -> Unit,
    openScreen: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: GroupDetailsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState
    val isOwner by viewModel.isOwner
    GroupDetailsScreenContent(
        uiState = uiState,
        isOwner = isOwner,
        openScreen = openScreen,
        onCreateExpenseClick = {
            viewModel.onCreateExpenseClick(openAndPopUp)
        },
        onViewExpenseClick = {viewModel.onViewExpenseClick(it, openAndPopUp)},
        removeGroupMember = {viewModel.removeGroupMember(it)}
    )
}

@Composable
fun GroupDetailsScreenContent(
    uiState: GroupDetailsScreenUiState,
    isOwner: Boolean,
    openScreen: (String) -> Unit,
    onCreateExpenseClick: () -> Unit,
    onViewExpenseClick: (String) -> Unit,
    removeGroupMember: (String) -> Unit
) {
    Log.d("APP", uiState.group.toString())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = uiState.group?.name.orEmpty(), fontSize = TextUnit(8.0F, TextUnitType.Em))
        Text(
            text = uiState.group?.description.orEmpty(),
            fontSize = TextUnit(5.0F, TextUnitType.Em)
        )
        BalanceSection(uiState = uiState)
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(uiState.groupMembers, key = { it.id }) { groupMember ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = groupMember.name)
                        if (isOwner && groupMember.id != uiState.group?.owner) {
                            IconButton(onClick = { removeGroupMember(groupMember.id) }) {
                                Icon(Icons.Filled.Delete, contentDescription = null)
                            }
                        }
                    }
                }

            }
        }
        Row(modifier = Modifier.fillMaxWidth(0.9F), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = onCreateExpenseClick, enabled = uiState.groupMembers.size > 1) {
                Text(text = "Add expense")
            }
            if (isOwner) {
                Button(onClick = { openScreen("$ADD_GROUP_MEMBER_SCREEN?$GROUP_ID=${uiState.group?.id.orEmpty()}") }) {
                    Text(text = "Add group member")
                }
            }
        }
        Column {
            Text(text = "Non paid expenses")
            LazyColumn {
                items(uiState.nonPaidExpenses, key = { it.id }) { expense ->
                    ExpenseItem(expense = expense, navigateToExpense = { onViewExpenseClick(expense.id) })
                }
            }
        }

        Column {
            Text(text = "My expenses")
            LazyColumn {
                items(uiState.myExpenses, key = { it.id }) { expense ->
                    ExpenseItem(expense = expense, navigateToExpense = { onViewExpenseClick(expense.id) })
                }
            }
        }

        Column {
            Text(text = "Paid expenses")
            LazyColumn {
                items(uiState.paidExpenses, key = { it.id }) { expense ->
                    ExpenseItem(expense = expense, navigateToExpense = { onViewExpenseClick(expense.id) })
                }
            }
        }

    }
}

@Composable
fun ExpenseItem(expense: GroupExpense, navigateToExpense: () -> Unit) {
    TextButton(onClick = { navigateToExpense() }) {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth(0.8F)) {
            Text(text = expense.creator.name)
            Text(text = "Total: ${expense.amount}")
        }
    }
}

@Composable
@Preview
fun GroupDetailsScreenPreview() {
    WeShareTheme {
        GroupDetailsScreenContent(uiState = GroupDetailsScreenUiState(
            group = Group(
                name = "Boys",
                description = "Amazing description",
                memberIds = emptyList()
            ),
            groupMembers = listOf(Profile(name = "Homer")),
            amountOwed = 100
        ), isOwner = true, openScreen = {}, onCreateExpenseClick = {}, onViewExpenseClick = {}, removeGroupMember = {})
    }
}