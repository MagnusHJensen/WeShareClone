package dk.sdu.weshareclone.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.ViewModel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navigation: NavController, profileViewModel: ProfileViewModel = koinViewModel()) {

    Column {
        Text("Hello ${profileViewModel.profile.value?.name}")
        Button(onClick = { signOut() }) {
            Text("Sign out")
        }
    }

}

fun signOut() {
    Firebase.auth.signOut()
}