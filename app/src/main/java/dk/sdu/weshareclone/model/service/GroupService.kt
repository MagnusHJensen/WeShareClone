package dk.sdu.weshareclone.model.service

import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.Profile

interface GroupService {
    suspend fun createGroup(name: String, description: String?, members: List<String>?)
    suspend fun updateGroup()
    suspend fun listGroups(): List<Group>
    suspend fun fetchGroupMembers(groupId: String): List<Profile>
    suspend fun leaveGroup()
}
