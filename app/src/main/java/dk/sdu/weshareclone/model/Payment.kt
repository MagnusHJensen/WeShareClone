package dk.sdu.weshareclone.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import java.util.UUID

data class Payment (
    @DocumentId val id: String = "",
    val amount: Int = 0,
    val owner: String,
    val requestedUsers: Map<String, Int>
)



