package dk.sdu.weshareclone.screens.group.group_details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.CREATE_EXPENSE_SCREEN
import dk.sdu.weshareclone.EXPENSE_ID
import dk.sdu.weshareclone.GROUP_ID
import dk.sdu.weshareclone.GROUP_SCREEN
import dk.sdu.weshareclone.VIEW_EXPENSE_SCREEN
import dk.sdu.weshareclone.model.Expense
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ExpenseService
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import dk.sdu.weshareclone.screens.group.group_details.models.GroupExpense
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
    val isOwner: MutableState<Boolean> = mutableStateOf(false)

    init {
        val groupId = savedStateHandle.get<String>(GROUP_ID)
        if (groupId != null) {
            launchCatching {
                val group = groupService.fetchGroup(groupId)
                val members = group.memberIds.map {
                    profileService.getProfile(it)
                }

                isOwner.value = group.owner == accountService.currentUserId

                val expenseList = expenseService.listExpenses(groupId)
                val moneyOwed = calculateMoneyOwed(expenseList);
                val moneyOws = calculateMoneyOws(expenseList);

                val mappedExpenses: List<GroupExpense> = expenseList.map {
                    GroupExpense(id = it.id, creator = profileService.getProfile(it.creator), amount = it.amount,
                        paid = it.creator == accountService.currentUserId || it.peopleSplit[accountService.currentUserId] == true
                    )
                }

                val myExpenses = mappedExpenses.filter {
                    it.creator.id == accountService.currentUserId
                }

                val paidExpenses = mappedExpenses.filter { // Expenses paid by the user that is not their own.
                    it.creator.id != accountService.currentUserId && it.paid
                }

                val nonPaidExpenses = mappedExpenses.filter {// Expenses not paid yet, that is not their own.
                    it.creator.id != accountService.currentUserId && !it.paid
                }

                uiState.value = uiState.value.copy(
                    group = group, groupMembers = members, amountOwed = moneyOwed,
                    amountOws = moneyOws, myExpenses = myExpenses, paidExpenses = paidExpenses, nonPaidExpenses = nonPaidExpenses
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

        val moneyOwed = ownExpenses.filterNot {
            it.peopleSplit.values.all { paid -> paid }// Filter out all paid expenses
        }.sumOf {
            val amountPaid = it.peopleSplit.entries.filter { entry -> entry.value && entry.key != accountService.currentUserId }.size
            it.amount - (it.amount / it.peopleSplit.keys.size) - (if (amountPaid > 0) it.amount / amountPaid else 0)
        }

        return moneyOwed
    }

    fun calculateMoneyOws(expenses: List<Expense>): Int {
        val notOwnExpenses = expenses.filter {
            it.creator != accountService.currentUserId // Not owner
                    && it.peopleSplit.containsKey(accountService.currentUserId) // Included in the people split
                    && it.peopleSplit[accountService.currentUserId] == false // Has not paid yet.
        }

        val moneyOws = notOwnExpenses.sumOf {
            it.amount / it.peopleSplit.keys.size
        }

        return moneyOws
    }

    fun onViewExpenseClick(expenseId: String, openAndPopUp: (String, String) -> Unit) {
        openAndPopUp("$VIEW_EXPENSE_SCREEN?$EXPENSE_ID=${expenseId}", "$GROUP_SCREEN?$GROUP_ID=${uiState.value.group?.id}")
    }
}