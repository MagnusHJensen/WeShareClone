package dk.sdu.weshareclone

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.appcheck.internal.util.Logger.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.ui.theme.WeShareCloneTheme

class EmailPasswordActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var emailInput: String = ""

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EmailInput(modifier: Modifier = Modifier) {
        TextField(value = emailInput, onValueChange = {
            emailInput = it
        }, modifier = modifier)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.auth.useEmulator("10.0.2.2", 9099) // Use emulator
        auth = Firebase.auth
        Log.d("Test", "Create $auth")

        setContent {
            WeShareCloneTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    EmailInput()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (::auth.isInitialized) {
            val currentUser = auth.currentUser
            Log.d("Test", "Start")
            if (currentUser != null) {
                reload();
            }
        }
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("App", "createUserWithEmail:Success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w("App", "createUserWithEmail:Failure")
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener {
            if (it.isSuccessful) {
                updateUI(auth.currentUser)
                Toast.makeText(baseContext, "Reload succesful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this@EmailPasswordActivity, MainActivity::class.java))
        } else {

        }
    }
}