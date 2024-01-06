package dk.sdu.weshareclone.screens.group.view_expense

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.EXPENSE_ID
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ExpenseService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.model.service.impl.NotificationSenderService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class ViewExpenseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val expenseService: ExpenseService,
    private val profileService: ProfileService,
    private val accountService: AccountService
) : WeShareViewModel() {
    val uiState = mutableStateOf(ViewExpenseUiState())
    private var currentExpenseId: String?

    init {
        val expenseId = savedStateHandle.get<String>(EXPENSE_ID)
        currentExpenseId = expenseId
        if (expenseId != null) {
            setupView(expenseId)
        }
    }

    private fun setupView(expenseId: String) {
        launchCatching {
            val expense = expenseService.getExpense(expenseId)
            val members = expense.peopleSplit.keys.map {
                profileService.getProfile(it)
            }

            val memberMap = emptyMap<Profile, Boolean>().toMutableMap()
            members.forEach {
                memberMap[it] = expense.peopleSplit[it.id] == true
            }

            val creator = profileService.getProfile(expense.creator)

            uiState.value = uiState.value.copy(
                reason = expense.reason, total = expense.amount, creator = creator,
                peopleSplit = memberMap, equalSplit = expense.amount / memberMap.size,
                isOwner = expense.creator == accountService.currentUserId,
                isPaid = expense.peopleSplit[accountService.currentUserId] == true,
                isPaying = false
            )
        }
    }

    fun sendNotification(notificationToken: String) {
        NotificationSenderService.sendFCMessage(notificationToken, "${uiState.value.creator?.name} wants you to pay!", "Please pay ${uiState.value.equalSplit} as soon as possible.")
    }

    fun payExpense() {
        launchCatching {
            if (currentExpenseId?.isNotEmpty() == true && !uiState.value.isPaying) {
                uiState.value = uiState.value.copy(isPaying = true)
                expenseService.payExpense(currentExpenseId!!)
                ViewModelStore().clear()
                setupView(currentExpenseId!!)

            }
        }


    }
}