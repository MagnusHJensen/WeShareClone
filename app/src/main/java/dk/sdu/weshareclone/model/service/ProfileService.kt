package dk.sdu.weshareclone.model.service

import dk.sdu.weshareclone.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileService {
    val profile: Flow<Profile?>

    suspend fun updateName(name: String)
    suspend fun createProfile(email: String)
    suspend fun getProfile(profileId: String): Profile
    suspend fun getProfileByEmail(email: String): Profile
}