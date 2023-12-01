package dk.sdu.weshareclone.model

import com.google.firebase.firestore.DocumentId

data class Group(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val memberIds: List<String> = emptyList()
)