package dk.sdu.weshareclone.model.service

import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.Profile

interface GroupService {
    suspend fun createGroup(name: String, description: String?, members: List<String>?)
    suspend fun listGroups(): List<Group>
    suspend fun fetchGroup(groupId: String): Group
    suspend fun updateGroup(group: Group)
    suspend fun removeGroupMember(groupId: String, memberId: String)
    suspend fun fetchGroupMembers(groupId: String): List<Profile>
    suspend fun inviteMember(groupId: String, email: String)
}
