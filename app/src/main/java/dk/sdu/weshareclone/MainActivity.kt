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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dk.sdu.weshareclone.model.ProfileModel
import dk.sdu.weshareclone.route.Screen
import dk.sdu.weshareclone.screens.HomeScreen
import dk.sdu.weshareclone.screens.LoginSceen
import dk.sdu.weshareclone.ui.theme.WeShareCloneTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            WeShareCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Navigation()
                }
            }
        }
    }

    @Composable
    fun Navigation() {

        val navigation = rememberNavController();


        Column {
            NavHost(
                navController = navigation,
                startDestination = Screen.LoginScreen.route
            ){
                composable(Screen.LoginScreen.route) { LoginSceen(navigation = navigation) }
                composable(Screen.HomeScreen.route) { HomeScreen(navigation = navigation)}
            }

        }

    }
}