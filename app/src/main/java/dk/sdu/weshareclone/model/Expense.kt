package dk.sdu.weshareclone.model

import com.google.firebase.firestore.DocumentId

data class  Expense(
    @DocumentId val id: String = "",
    val groupId: String = "",
    val reason: String = "",
    val amount: Int = 0,
    val creator: String = "", // The person who created the expense
    val peopleSplit: MutableMap<String, Boolean> = mutableMapOf() // A map of all included people in this expense, including whether or not they have paid their part.

) {

    fun payExpense(payer: String) {
        peopleSplit[payer] = true
    }

    companion object {
        operator fun invoke(id: String = "",
                            groupId: String = "",
                            reason: String = "",
                            amount: Int = 0,
                            creator: String = "",
                            people: List<String> = emptyList()): Expense {
            val peopleSplit = mutableMapOf<String, Boolean>()
            people.forEach {
                peopleSplit[it] = it == creator // Owner of the expense has already paid it.
            }
            return Expense(id, groupId, reason, amount, creator, peopleSplit)
        }
    }
}