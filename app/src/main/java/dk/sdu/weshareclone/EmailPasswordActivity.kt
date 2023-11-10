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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.ui.theme.WeShareCloneTheme

class EmailPasswordActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EmailInput(email: String, onEmailChange: (String) -> Unit) {
        Column {
            Text(text = "Email")
            TextField(singleLine = true, value = email, onValueChange = onEmailChange, modifier = Modifier. border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PasswordInput(password: String, onPasswordChange: (String) -> Unit) {
        var isError by remember { mutableStateOf(false) }
        val minCharLimit = 6
        fun validate(text: String) {
            isError = text.length < minCharLimit
        }

        Column {
            Text(text = "Password")
            TextField(value = password, isError = isError, onValueChange = {
                onPasswordChange(it)
                validate(password)
            }, singleLine = true, supportingText = {
                if (isError) {
                    Text("Password must be minimum 6 characters long.")
                }
            },trailingIcon = {
                if (isError)
                    Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
            }, modifier = Modifier. border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
        }
    }

    @Composable
    fun CreateAccountForm() {
        //TODO: Set an error attribute, that gets set from create Account and or signIn.
        // Potentially create a wrapper function for those, that can catch the exception thrown and then set the error message accordingly.

        fun wrapCreateAccount(email: String, password: String) {
            try {
                createAccount(email, password)
            } catch (e: FirebaseAuthException) {
                if (e is FirebaseAuthEmailException) {
                    Log.d("APP", "Bad email")
                }
            }
        }

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Column {
            EmailInput(email, onEmailChange = { email = it})
            PasswordInput(password, onPasswordChange = { password = it})
            Row {
                Button(onClick = { wrapCreateAccount(email, password) }, enabled = email.isNotEmpty() && password.isNotEmpty()) {
                    Text("Create account")
                }
                Button(onClick = { signIn(email, password) }, enabled = email.isNotEmpty() && password.isNotEmpty()) {
                    Text("Sign in")
                }
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
                reload()
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
                    val ref = Firebase.firestore.document("profiles/${auth.currentUser?.uid}")
                    val document = ref.get()
                    updateUI(user, document.isSuccessful)
                } else {
                    Log.w("App", "signInWithEmail:Failure")
                    Toast.makeText(baseContext, "Authentication failed.\n${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    updateUI(null, false)
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
                    updateUI(user, false)
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthEmailException) {
                        Toast.makeText(baseContext, "FirebaseAuthEmail", Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        Toast.makeText(baseContext, "Password must be at minimum 6 characters", Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(baseContext, "Email is badly formatted.", Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthUserCollisionException) {
                        Toast.makeText(baseContext, "Email is already in use", Toast.LENGTH_SHORT).show()
                    }
                    updateUI(null, false)
                }

            }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener {
            if (it.isSuccessful) {

                Firebase.firestore.document("profiles/${auth.currentUser?.uid}").get()
                    .addOnCompleteListener {
                        updateUI(auth.currentUser, it.isSuccessful)
                        Log.d("APP", "Complete ${it.result.data}")
                    }
                    .addOnFailureListener {
                        Log.d("APP", "Failure: ${it.message}")
                    }

            } else {
                Toast.makeText(baseContext, "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?, hasProfile: Boolean) {
        if (user != null) {
            if (hasProfile)
                startActivity(Intent(this@EmailPasswordActivity, MainActivity::class.java))
            else
                startActivity(Intent(this@EmailPasswordActivity, PickNameActivity::class.java))
        } else {

        }
    }
}