package dk.sdu.weshareclone.screens.group.group_details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.CREATE_EXPENSE_SCREEN
import dk.sdu.weshareclone.GROUP_ID
import dk.sdu.weshareclone.GROUP_SCREEN
import dk.sdu.weshareclone.model.Expense
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ExpenseService
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class GroupDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val groupService: GroupService,
    private val profileService: ProfileService,
    private val expenseService: ExpenseService,
    private val accountService: AccountService
) : WeShareViewModel() {
    val uiState = mutableStateOf(GroupDetailsScreenUiState())

    init {
        val groupId = savedStateHandle.get<String>(GROUP_ID)
        Log.d("APP", "Got $groupId from routing")
        if (groupId != null) {
            launchCatching {
                val group = groupService.fetchGroup(groupId)
                val members = group.memberIds.map {
                    profileService.getProfile(it)
                }

                val expenseList = expenseService.listExpenses(groupId)
                val moneyOwed = calculateMoneyOwed(expenseList);
                val moneyOws = calculateMoneyOws(expenseList);
                uiState.value = uiState.value.copy(
                    group = group, groupMembers = members, amountOwed = moneyOwed,
                    amountOws = moneyOws
                )
            }
        }
    }

    fun onCreateExpenseClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(
            "$CREATE_EXPENSE_SCREEN?$GROUP_ID=${uiState.value.group?.id}",
            "$GROUP_SCREEN?$GROUP_ID=${uiState.value.group?.id}"
        )
    }

    fun calculateMoneyOwed(expenses: List<Expense>): Int {
        val ownExpenses = expenses.filter {
            it.creator == accountService.currentUserId
        }

        val moneyOwed = ownExpenses.sumOf {
            it.amount - (it.amount / it.peopleSplit.keys.size)
        }

        return moneyOwed
    }

    fun calculateMoneyOws(expenses: List<Expense>): Int {
        val notOwnExpenses = expenses.filter {
            it.creator != accountService.currentUserId // Not owner
                    && it.peopleSplit.containsKey(accountService.currentUserId) // Included in the people split
                    && !it.peopleSplit[accountService.currentUserId]!! // Has not paid yet.
        }

        val moneyOws = notOwnExpenses.sumOf {
            it.amount / it.peopleSplit.keys.size
        }

        return moneyOws
    }
}