package dk.sdu.weshareclone.screens.create_expense_screen

import androidx.compose.runtime.mutableStateListOf
import dk.sdu.weshareclone.model.Profile


data class CreateExpenseUiState(
    val reason: String = "",
    val amount: String = "0",
    val groupName: String = "",
    val groupMembers: List<Profile> = emptyList(),
    /**
     * Map containing all people in the group, and whether or not they are included in this expense.
     */
    val includedPeople: MutableList<String> = mutableStateListOf()
)