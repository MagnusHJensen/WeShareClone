package dk.sdu.weshareclone.model

import com.google.firebase.firestore.DocumentId

data class Payment (
    @DocumentId val id: String = "",
    val amount: String = "0",
    val owner: String = "",
    val requestedUsers: Map<String, Int> = HashMap(),
)



