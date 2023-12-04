package dk.sdu.weshareclone.model

import com.google.firebase.firestore.DocumentId

data class Group(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val owner: String = "",
    var memberIds: List<String> = emptyList()
) {

    fun addMember(memberId: String) {
        val newMemberIds = mutableListOf<String>()
        newMemberIds.add(memberId)
        newMemberIds.addAll(memberIds)

        memberIds = newMemberIds
    }
}