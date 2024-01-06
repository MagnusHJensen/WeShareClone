package dk.sdu.weshareclone.screens.home_screen

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.CREATE_GROUP_SCREEN
import dk.sdu.weshareclone.GROUP_ID
import dk.sdu.weshareclone.GROUP_SCREEN
import dk.sdu.weshareclone.components.RequestNotificationPermissionDialog
import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    openScreen: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState(initial = HomeScreenUiState())
    val groups by viewModel.groups

    LaunchedEffect(true) {
        viewModel.fetchGroups()
    }

    HomeScreenContent(
        uiState,
        groups = groups,
        openScreen = openScreen,
        onInspectProfile = { viewModel.onInspectProfile(openAndPopUp)}
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    groups: List<Group>,
    openScreen: (String) -> Unit,
    onInspectProfile: () -> Unit
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermissionDialog()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = "Hello ${uiState.name}")
        LazyColumn {
            items(groups, key = { it.id }) { groupItem ->
                Button(onClick = { openScreen("$GROUP_SCREEN?$GROUP_ID=${groupItem.id}") }) {
                    Text(text = groupItem.name)
                }

            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = {openScreen(CREATE_GROUP_SCREEN)}) {
                Text(text = "Create group")
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    val uiState = HomeScreenUiState(name = "Preview", isLoaded = true)
    val groups = listOf(
        Group("123", "Boys", "", "", emptyList())
    )


    WeShareTheme {
        HomeScreenContent(
            uiState = uiState,
            groups = groups,
            openScreen = {},
            onInspectProfile = {})
    }
}