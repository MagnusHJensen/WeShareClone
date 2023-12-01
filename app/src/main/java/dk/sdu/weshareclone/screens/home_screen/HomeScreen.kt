package dk.sdu.weshareclone.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.ui.theme.WeShareTheme
import kotlin.random.Random

@Composable
fun HomeScreen(
    restartApp: (String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit,
    viewGroup: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState(initial = HomeScreenUiState())

    HomeScreenContent(
        uiState,
        groups = viewModel.groups,
        onSignOutClick = { viewModel.onSignOutClick(restartApp) },
        createGroup = viewModel::createGroup,
        viewGroup = viewGroup
    )

}

@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    groups: List<Group>,
    onSignOutClick: () -> Unit,
    createGroup: () -> Unit,
    viewGroup: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = "Hello ${uiState.name}")
        LazyColumn {
            items(groups, key = { it.id }) { groupItem ->
                Button(onClick = {viewGroup(groupItem.id)}) {
                    Text(text = groupItem.name)
                }
                /*Card(
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
                }*/
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = onSignOutClick) {
                Text(text = "Sign out")
            }
            Button(onClick = createGroup) {
                Text(text = "Create group")
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
    val groups = listOf(
        Group("123", "Boys", "", emptyList())
    )


    WeShareTheme {
        HomeScreenContent(
            uiState = uiState,
            groups = groups,
            onSignOutClick = {},
            createGroup = {},
            viewGroup = {})
    }
}