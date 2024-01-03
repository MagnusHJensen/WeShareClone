package dk.sdu.weshareclone.screens.group.group_details.models

import dk.sdu.weshareclone.model.Profile

data class GroupExpense(
    val id: String,
    val creator: Profile,
    val amount: Int,
    val paid: Boolean
)