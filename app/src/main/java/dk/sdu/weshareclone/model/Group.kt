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

    fun removeMember(memberId: String) {
        val newMemberIds = mutableListOf<String>()
        memberIds.forEach {
            if (it != memberId) {
                newMemberIds.add(it)
            }
        }

        memberIds = newMemberIds
    }
}