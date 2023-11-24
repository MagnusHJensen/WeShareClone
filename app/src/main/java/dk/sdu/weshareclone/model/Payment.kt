package dk.sdu.weshareclone.model

import com.google.firebase.firestore.DocumentId
import java.util.UUID

data class Payment (
    @DocumentId val id: UUID = UUID.randomUUID(),
    val amount: String = "",
    val requestedUsers: Map<String, Int>
)



