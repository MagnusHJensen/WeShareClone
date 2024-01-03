package dk.sdu.weshareclone.screens.group.group_details

import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.screens.group.group_details.models.GroupExpense

data class GroupDetailsScreenUiState(
    val group: Group? = null,
    val groupMembers: List<Profile> = emptyList(),
    val myExpenses: List<GroupExpense> = emptyList(),
    val nonPaidExpenses: List<GroupExpense> = emptyList(),
    val paidExpenses: List<GroupExpense> = emptyList(),
    /**
     * The amount the person is owed from other group members
     */
    val amountOwed: Int = 0,
    /**
     * The amount the person owes to other group members
     */
    val amountOws: Int = 0
) {
    /**
     * Checks if the person logged in has their balance in check, to display an alternative text.
     */
    fun isBalanceInCheck(): Boolean {
        return amountOws == 0 && amountOwed == 0
    }
}