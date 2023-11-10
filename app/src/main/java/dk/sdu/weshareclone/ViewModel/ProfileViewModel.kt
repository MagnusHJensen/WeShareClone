package dk.sdu.weshareclone.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.model.ProfileModel

class ProfileViewModel: ViewModel() {

    val profile = mutableStateOf<ProfileModel?>(null)
    val isLoading = mutableStateOf(false)

    init {
        fetchProfileAsync()
    }

    private fun fetchProfileAsync() {
        Firebase.firestore.document("profiles/${Firebase.auth.currentUser!!.uid}").get()
            .addOnSuccessListener {
                profile = it.toObject(ProfileModel::class.java)
            }
    }
}