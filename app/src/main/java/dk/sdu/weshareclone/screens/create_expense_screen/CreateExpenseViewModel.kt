package dk.sdu.weshareclone.screens.create_expense_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.GROUP_ID
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ExpenseService
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.screens.WeShareViewModel
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val accountService: AccountService,
    private val groupService: GroupService,
    private val profileService: ProfileService,
    private val expenseService: ExpenseService
) : WeShareViewModel() {
    val uiState = mutableStateOf(CreateExpenseUiState())


    init {
        val groupId = savedStateHandle.get<String>(GROUP_ID)
        if (groupId != null) {
            launchCatching {
                val group = groupService.fetchGroup(groupId = groupId)
                val groupMembers = group.memberIds.map {
                    profileService.getProfile(it)
                }
                    .filter { it.id != accountService.currentUserId } // Exclude self from list of people, since owner is always included.

                uiState.value = uiState.value.copy(
                    groupName = group.name,
                    groupMembers = groupMembers
                )
            }
        }
    }

    fun onAmountChange(newAmount: String) {
        uiState.value = uiState.value.copy(amount = newAmount)
    }

    fun onCheckPerson(profile: Profile) {
        uiState.value.includedPeople.add(profile.id)
    }

    fun createExpense(popUp: () -> Unit) {
        val groupId = savedStateHandle.get<String>(GROUP_ID) ?: return
        launchCatching {
            val includedPeople = uiState.value.includedPeople
            includedPeople.add(accountService.currentUserId) // Add back owner to internal list.

            expenseService.createExpense(
                Integer.parseInt(uiState.value.amount),
                includedPeople,
                groupId
            )
            popUp()
        }
    }

}