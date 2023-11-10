package dk.sdu.weshareclone

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.model.ProfileModel
import dk.sdu.weshareclone.ui.theme.WeShareCloneTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            WeShareCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LandingPage()
                }
            }
        }
    }

    @Composable
    fun LandingPage() {
        var profile by remember {
            mutableStateOf<ProfileModel?>(null)
        }

        Firebase.firestore.document("profiles/${Firebase.auth.currentUser!!.uid}").get()
            .addOnSuccessListener {
                profile = it.toObject(ProfileModel::class.java)
            }


        Firebase.auth.addAuthStateListener {
            if (it.currentUser == null) {
                this.startActivity(Intent(this@MainActivity, EmailPasswordActivity::class.java))
            }
        }

        Column {
            Greeting("Android ${profile?.name}")
            Button(onClick = { signOut() }) {
                Text("Sign out")
            }
        }

    }


}



fun signOut() {
    Firebase.auth.signOut()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeShareCloneTheme {
        Greeting("Android")
    }
}