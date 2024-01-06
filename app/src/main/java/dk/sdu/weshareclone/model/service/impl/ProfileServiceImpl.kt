package dk.sdu.weshareclone.model.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.dataObjects
import com.google.firebase.messaging.FirebaseMessaging
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ProfileService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    messaging: FirebaseMessaging,
    private val accountService: AccountService
) : ProfileService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val profile: Flow<Profile?>
        get() =
            accountService.currentUser.flatMapLatest { user ->
                if (accountService.hasUser) {
                    val document = firestore.collection(PROFILE_COLLECTION).document(user.id)
                    document.addSnapshotListener { snapshot, e ->
                        snapshot?.let {
                            it.toObject(Profile::class.java)
                        }
                    }

                    document.dataObjects()
                } else {
                    flowOf(null)
                }
            }

    init {
        messaging.token.addOnCompleteListener {
            if (it.isSuccessful) {
                val token = it.result
                token?.let {token ->
                    updateNotificationToken(accountService.currentUserId, token)
                }
            }
        }
    }

    private fun updateNotificationToken(userId: String, token: String) {
        val userTokenMap = mapOf("notificationToken" to token)

        firestore.collection(PROFILE_COLLECTION).document(userId).set(userTokenMap, SetOptions.merge())
    }

    override suspend fun updateName(newName: String) {
        var currentProfile = profile.first()
        currentProfile =
            currentProfile?.copy(name = newName) ?: Profile(accountService.currentUserId, newName)

        firestore.collection(PROFILE_COLLECTION).document(currentProfile.id).set(currentProfile)
            .await()
    }

    override suspend fun updateEmail(newEmail: String) {
        var currentProfile = profile.first()
        currentProfile =
            currentProfile?.copy(email = newEmail) ?: Profile(id = accountService.currentUserId, email = newEmail)

        firestore.collection(PROFILE_COLLECTION).document(currentProfile.id).set(currentProfile).await()
    }

    override suspend fun createProfile(email: String) {
        firestore.collection(PROFILE_COLLECTION).document(accountService.currentUserId)
            .set(Profile(id = accountService.currentUserId, email = email)).await()
    }

    override suspend fun getProfile(profileId: String): Profile {
        return firestore.collection(PROFILE_COLLECTION).document(profileId).get().await()
            .toObject(Profile::class.java) ?: throw Exception("Can not fetch this profile")
    }

    override suspend fun getProfileByEmail(email: String): Profile {
        val profiles = firestore.collection(PROFILE_COLLECTION).whereEqualTo(EMAIL_FIELD, email).limit(1).get().await()
            .toObjects(Profile::class.java)

        if (profiles.isEmpty()) {
            throw Exception("No profile exists")
        }

        return profiles[0]
    }

    companion object {
        private const val PROFILE_COLLECTION = "profiles"
        private const val EMAIL_FIELD = "email"
    }
}