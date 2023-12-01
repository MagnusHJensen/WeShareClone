package dk.sdu.weshareclone.model.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.model.service.ProfileService
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class GroupServiceImpl @Inject constructor(private val firestore: FirebaseFirestore, private val profileService: ProfileService, private val accountService: AccountService) : GroupService {
    override suspend fun createGroup(name: String, description: String?, members: List<String>?) {
        val id = UUID.randomUUID().toString()
        val group = Group(id, name, description.orEmpty(), members.orEmpty())
        firestore.collection(GROUP_COLLECTION).document(id).set(group).await()
    }

    override suspend fun updateGroup() {
        TODO("Not yet implemented")
    }

    override suspend fun listGroups(): List<Group> {
        return firestore.collection(GROUP_COLLECTION)
            .whereArrayContains(MEMBERS_ID_FIELD, accountService.currentUserId)
            .get().await().toObjects(Group::class.java)
    }

    override suspend fun fetchGroupMembers(groupId: String): List<Profile> {
        val result = firestore.collection(GROUP_COLLECTION).document(groupId).get().await()
        if (!result.exists()) throw Exception("Group does not exist")
        val group = result.toObject(Group::class.java)
        val profiles = mutableListOf<Profile>()
        group?.memberIds?.forEach { memberId ->
            profileService.getProfile(memberId)?.let {
                profiles.add(it)
            }
        }

        return profiles

    }

    override suspend fun leaveGroup() {
        TODO("Not yet implemented")
    }

    companion object {
        const val GROUP_COLLECTION = "groups"
        const val MEMBERS_ID_FIELD = "memberIds"
    }
}