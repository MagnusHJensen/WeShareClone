package dk.sdu.weshareclone.screens.group.view_expense

import dk.sdu.weshareclone.model.Profile

data class ViewExpenseUiState(
    val reason: String = "",
    val total: Int = 0,
    val equalSplit: Int = 0,
    val creator: Profile? = null,
    val peopleSplit: Map<Profile, Boolean> = emptyMap() // Map of included users and a boolean value to show if you're the owner, wether or not they have åaod-
) 