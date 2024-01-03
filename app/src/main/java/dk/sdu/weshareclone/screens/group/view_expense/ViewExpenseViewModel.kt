package dk.sdu.weshareclone.screens.group.view_expense

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ExpenseService
import dk.sdu.weshareclone.model.service.ProfileService
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

}