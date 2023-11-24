package dk.sdu.weshareclone.screens.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.PAYMENT_SCREEN
import dk.sdu.weshareclone.model.Payment

@Composable
fun HomeScreen(
    restartApp: (String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState(initial = HomeScreenUiState())

    HomeScreenContent(
        uiState,
        ownedPayments = viewModel.ownedPayments.collectAsStateWithLifecycle(initialValue = emptyList()),
        onSignOutClick = { viewModel.onSignOutClick(restartApp) },
        createPayment = {openAndPopUp(PAYMENT_SCREEN, HOME_SCREEN)}
    )

}

@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    ownedPayments: State<List<Payment>>,
    onSignOutClick: () -> Unit,
    createPayment: () -> Unit
) {
    Column {
            Text(text = "Hello ${uiState.name}")
            LazyColumn {
                items(ownedPayments.value, key = { it.id }) { paymentItem ->
                    Card {
                        Row {
                            Text(text = paymentItem.amount)
                        }
                    }
                }
            }
            Button(onClick = onSignOutClick) {
                Text(text = "Sign out")
            }
            Button(onClick = createPayment) {
                Text(text = "Create payment")
            }

    }
}