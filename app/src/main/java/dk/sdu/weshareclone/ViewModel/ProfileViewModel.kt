package dk.sdu.weshareclone.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.model.ProfileModel
import dk.sdu.weshareclone.repositories.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository): ViewModel() {


    val profile = mutableStateOf<ProfileModel?>(null)
    val isLoading = mutableStateOf(false)

    init {
        fetchProfle()
    }

    fun fetchProfle() {
        isLoading.value = true
        viewModelScope.launch {
            profile.value = repository.getProfile()
        }
    }

}