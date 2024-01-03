package dk.sdu.weshareclone.model.service.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import dk.sdu.weshareclone.model.Expense
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ExpenseService
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class ExpenseServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val accountService: AccountService
) : ExpenseService {
    override suspend fun createExpense(reason:String, amount: Int, people: List<String>, groupId: String) {
        val expense = Expense(
            id = UUID.randomUUID().toString(),
            groupId = groupId,
            reason = reason,
            amount = amount,
            creator = accountService.currentUserId,
            people = people
        )

        firestore.collection(EXPENSE_COLLECTION).document(expense.id).set(expense).await()
    }

    override suspend fun listExpenses(groupId: String): List<Expense> {
        Log.d("APP", "Listing expenses for $groupId")
        return firestore
            .collection(EXPENSE_COLLECTION)
            .whereEqualTo(GROUP_ID_FIELD, groupId)
            .get()
            .await()
            .toObjects(Expense::class.java)
    }

    override suspend fun getExpense(expenseId: String): Expense {
        return firestore
            .collection(EXPENSE_COLLECTION)
            .document(expenseId)
            .get()
            .await()
            .toObject(Expense::class.java) ?: throw Exception("Could not find expense")
    }

    private suspend fun updateExpense(expense: Expense) {
        firestore
            .collection(EXPENSE_COLLECTION)
            .document(expense.id)
            .set(expense)
            .await()
    }

    override suspend fun payExpense(expenseId: String) {
        val expense = getExpense(expenseId)
        expense.payExpense(accountService.currentUserId)
        updateExpense(expense)
    }

    override suspend fun deleteExpense(expenseId: String) {
        TODO("Not yet implemented")
    }

    companion object {
        const val EXPENSE_COLLECTION = "expenses"
        const val GROUP_ID_FIELD = "groupId"
    }

}