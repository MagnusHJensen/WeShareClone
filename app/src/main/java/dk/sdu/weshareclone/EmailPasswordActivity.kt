package dk.sdu.weshareclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.ui.theme.WeShareCloneTheme

class EmailPasswordActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    @Composable
    fun EmailInput(email: String, onEmailChange: (String) -> Unit) {
        Column {
            Text(text = "Email")
            BasicTextField(value = email, onValueChange = onEmailChange, modifier = Modifier. border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
        }
    }

    @Composable
    fun PasswordInput(password: String, onPasswordChange: (String) -> Unit) {
        Column {
            Text(text = "Password")
            BasicTextField(value = password, onValueChange = onPasswordChange, modifier = Modifier. border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
        }
    }

    @Composable
    fun CreateAccountForm() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Column {
            EmailInput(email, onEmailChange = { email = it})
            PasswordInput(password, onPasswordChange = { password = it})
            Button(onClick = { createAccount(email, password) }) {
                Text("Create account")
            }
            Button(onClick = { signIn(email, password) }) {
                Text("Sign in")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth



        setContent {
            WeShareCloneTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CreateAccountForm()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (::auth.isInitialized) {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                reload();
            }
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("App", "signInWithEmail:Success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w("App", "signInWithEmail:Failure")
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
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