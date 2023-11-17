package dk.sdu.weshareclone.model.service

import dk.sdu.weshareclone.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileService {
    val profile: Flow<Profile?>

    suspend fun updateName(name: String)
}