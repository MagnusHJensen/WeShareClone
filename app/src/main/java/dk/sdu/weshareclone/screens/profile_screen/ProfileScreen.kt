package dk.sdu.weshareclone.screens.profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.HOME_SCREEN
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun ProfileScreen(openScreen: (String) -> Unit, viewModel: ProfileViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    ProfileScreenContent(
        uiState = uiState,
        openScreen = openScreen,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onNameUpdate =  viewModel::onUpdateName,
        onEmailUpdate = viewModel::onUpdateEmail
    )
}

@Composable
fun ProfileScreenContent(
    uiState: ProfileUiState,
    openScreen: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNameUpdate: () -> Unit,
    onEmailUpdate: () -> Unit
) {
    Column {
        Text(text = "Current name:")
        TextField(value = uiState.name, onValueChange = onNameChange, singleLine = true, modifier = Modifier.border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
        Text(text = "Current email:")
        TextField(value = uiState.email, onValueChange = onEmailChange)

        Row {
            Button(onClick = onNameUpdate) {
                Text(text = "Update name")
            }
            Button(onClick = onEmailUpdate) {
                Text(text = "Update email")
            }
            Button(onClick = { openScreen(HOME_SCREEN) }) {
                Text(text = "Back")
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    WeShareTheme {
    }
}
