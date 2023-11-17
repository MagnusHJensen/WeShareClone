package dk.sdu.weshareclone.model.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
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

class ProfileServiceImpl @Inject constructor(private val firestore: FirebaseFirestore, private val accountService: AccountService) : ProfileService {

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

    override suspend fun updateName(newName: String) {
        var currentProfile = profile.first()
        currentProfile =
            currentProfile?.copy(name = newName) ?: Profile(accountService.currentUserId, newName)

        firestore.collection(PROFILE_COLLECTION).document(currentProfile.id).set(currentProfile).await()
    }

    companion object {
        private const val PROFILE_COLLECTION = "profiles"
    }
}