package dk.sdu.weshareclone.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.PAYMENT_SCREEN
import dk.sdu.weshareclone.model.Payment
import dk.sdu.weshareclone.model.Profile
import dk.sdu.weshareclone.ui.theme.WeShareTheme
import kotlin.random.Random

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
        createPayment = { openAndPopUp(PAYMENT_SCREEN, HOME_SCREEN) }
    )

}

@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    ownedPayments: State<List<PaymentViewModel>>,
    onSignOutClick: () -> Unit,
    createPayment: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = "Hello ${uiState.name}")
        LazyColumn {
            items(ownedPayments.value, key = { it.payment.id }) { paymentItem ->
                Card(
                    modifier = Modifier
                        .width(380.dp)
                        .height(100.dp)

                ) {

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row {
                            Text(text = "Total: ")
                            Text(text = paymentItem.payment.amount)
                            Text(text = paymentItem.payment.requestedUsers.entries.toString())
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy((-20).dp),
                            ) {
                                UserAvatar(
                                    value = paymentItem.owner.name.first().toString(),
                                    modifier = Modifier.zIndex(4.0F)
                                )
                                UserAvatar(
                                    value = paymentItem.owner.name.first().toString(),
                                    modifier = Modifier.zIndex(3.0F)
                                )
                                UserAvatar(
                                    value = paymentItem.owner.name.first().toString(),
                                    modifier = Modifier.zIndex(2.0F)
                                )
                                UserAvatar(value = "...", modifier = Modifier.zIndex(1.0F))
                            }
                            UserAvatar(value = paymentItem.owner.name.first().toString())
                        }
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = onSignOutClick) {
                Text(text = "Sign out")
            }
            Button(onClick = createPayment) {
                Text(text = "Create payment")
            }
        }

    }
}

@Composable
fun UserAvatar(value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .size(35.dp)
            .background(
                color = Color(
                    Random.nextInt(255),
                    Random.nextInt(255),
                    Random.nextInt(255)
                ), shape = RoundedCornerShape(50)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = value)
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    val uiState = HomeScreenUiState(name = "Preview", isLoaded = true)
    val listOfPayments = listOf(
        PaymentViewModel(Payment("test", "100", "123", HashMap()), Profile("test", "User"))
    )
    val ownedPayments = remember {
        mutableStateOf(listOfPayments)
    }

    WeShareTheme {
        HomeScreenContent(
            uiState = uiState,
            ownedPayments = ownedPayments,
            onSignOutClick = {},
            createPayment = {})
    }
}