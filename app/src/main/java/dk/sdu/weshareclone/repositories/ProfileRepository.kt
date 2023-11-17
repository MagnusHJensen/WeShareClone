package dk.sdu.weshareclone.repositories

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dk.sdu.weshareclone.model.ProfileModel

class ProfileRepository {
    var currentUser: ProfileModel? = null


    init {
        attachListeners()
    }

    fun authStateListener(auth: FirebaseAuth) {
        if (currentUser == null && auth.currentUser != null) {
            Firebase.firestore.document("profiles/${Firebase.auth.currentUser!!.uid}").get()
                .addOnSuccessListener {
                    currentUser = it.toObject(ProfileModel::class.java)
                }
        }
    }

    fun attachListeners() {
        Firebase.auth.addAuthStateListener {
            authStateListener(it)
        }
    }

    fun getProfile(): ProfileModel? {
        return currentUser;
    }

}