package dk.sdu.weshareclone

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.model.ProfileModel
import dk.sdu.weshareclone.ui.theme.WeShareCloneTheme

class PickNameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            WeShareCloneTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    PickNameForm()
                }
            }
        }
    }

    @Composable
    fun PickNameForm() {
        var name by remember {
            mutableStateOf("")
        }

        fun pickName() {
            Firebase.firestore.document("profiles/${Firebase.auth.currentUser?.uid}").set(
                ProfileModel(name, docId = Firebase.auth.currentUser!!.uid)
            )
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        startActivity(Intent(this@PickNameActivity, MainActivity::class.java))
                    }
                }
        }

        Column {
            NameInput(name = name, onNameChange = {name = it} )
            Button(onClick = { pickName() }, enabled = name.isNotEmpty()) {
                Text("Continue")
            }
        }
    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameInput(name: String, onNameChange: (String) -> Unit) {
    Text("Pick your name")
    TextField(value = name, onValueChange = onNameChange)
}