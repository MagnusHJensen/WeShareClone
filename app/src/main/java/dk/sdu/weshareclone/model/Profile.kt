package dk.sdu.weshareclone.model

import com.google.firebase.firestore.DocumentId

data class Profile(
    @DocumentId val id: String = "",
    val name: String = "",
    val email: String = ""
)