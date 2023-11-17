package dk.sdu.weshareclone.model.service

import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun createAccount(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}