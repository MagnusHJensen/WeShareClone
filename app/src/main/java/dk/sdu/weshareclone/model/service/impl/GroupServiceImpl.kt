package dk.sdu.weshareclone.model.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ExpenseService
import dk.sdu.weshareclone.model.service.GroupService
import dk.sdu.weshareclone.model.service.ProfileService
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class GroupServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val profileService: ProfileService,
    private val accountService: AccountService,
    private val expenseService: ExpenseService
) : GroupService {
    override suspend fun createGroup(name: String, description: String?, members: List<String>?) {
        val id = UUID.randomUUID().toString()
        val group = Group(
            id = id,
            name = name,
            description = description.orEmpty(),
            owner = accountService.currentUserId,
            memberIds = members.orEmpty()
        )
        firestore.collection(GROUP_COLLECTION).document(id).set(group).await()
    }

    override suspend fun updateGroup(group: Group) {
        firestore.collection(GROUP_COLLECTION).document(group.id).set(group).await()
    }

    override suspend fun listGroups(): List<Group> {
        return firestore.collection(GROUP_COLLECTION)
            .whereArrayContains(MEMBERS_ID_FIELD, accountService.currentUserId)
            .get().await().toObjects(Group::class.java)
    }

    override suspend fun fetchGroupMembers(groupId: String): List<Profile> {
        val group = fetchGroup(groupId)
        val profiles = mutableListOf<Profile>()
        group.memberIds.forEach { memberId ->
            profiles.add(profileService.getProfile(memberId))
        }

        return profiles

    }

    override suspend fun removeGroupMember(groupId: String, memberId: String) {
        val group = fetchGroup(groupId)
        if (!group.memberIds.contains(memberId) || accountService.currentUserId != group.owner) {
            return
        }

        group.removeMember(memberId)
        updateGroup(group)
        expenseService.deleteExpenseByUser(groupId, memberId)
    }

    override suspend fun fetchGroup(groupId: String): Group {
        return firestore
            .collection(GROUP_COLLECTION)
            .document(groupId)
            .get()
            .await()
            .toObject(Group::class.java) ?: throw Exception("Group does not exist")
    }

    override suspend fun inviteMember(groupId: String, email: String) {
        val profile = profileService.getProfileByEmail(email)
        val group = fetchGroup(groupId = groupId)

        if (group.owner != accountService.currentUserId) {
            throw Exception("Only the owner can invite other members")
        }

        if (group.memberIds.contains(profile.id)) {
            throw Exception("User is already a part of the group.")
        }

        group.addMember(profile.id)
        updateGroup(group = group)
    }

    companion object {
        const val GROUP_COLLECTION = "groups"
        const val MEMBERS_ID_FIELD = "memberIds"
    }
}