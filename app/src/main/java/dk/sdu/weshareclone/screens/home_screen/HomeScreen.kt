package dk.sdu.weshareclone.screens.home_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.PAYMENT_SCREEN
import dk.sdu.weshareclone.PICK_NAME_SCREEN
import dk.sdu.weshareclone.model.Profile
import kotlinx.coroutines.flow.observeOn

@Composable
fun HomeScreen(
    restartApp: (String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState(initial = HomeScreenUiState())

    HomeScreenContent(
        uiState,
        onSignOutClick = { viewModel.onSignOutClick(restartApp) },
        createPayment = {openAndPopUp(PAYMENT_SCREEN, HOME_SCREEN)}
    )

}

@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    onSignOutClick: () -> Unit,
    createPayment: () -> Unit
) {
    Column {

            Text(text = "Hello ${uiState.name}")
            Button(onClick = onSignOutClick) {
                Text(text = "Sign out")
            }
            Button(onClick = createPayment) {
                Text(text = "Create payment")
            }

    }
}