package dk.sdu.weshareclone.model.service

import dk.sdu.weshareclone.model.Expense

interface ExpenseService {
    suspend fun createExpense(amount: Int, people: List<String>, groupId: String)
    suspend fun listExpenses(groupId: String): List<Expense>
    suspend fun getExpense(expenseId: String): Expense
    suspend fun payExpense(expenseId: String)
    suspend fun deleteExpense(expenseId: String)

}