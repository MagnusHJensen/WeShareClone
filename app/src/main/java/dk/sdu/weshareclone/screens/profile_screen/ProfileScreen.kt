package dk.sdu.weshareclone.screens.profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun ProfileScreen(openAndPopUp: (String, String) -> Unit, viewModel: ProfileViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    ProfileScreenContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onPickName = { /*TODO*/ },
        onEmailChange = viewModel::onEmailChange
    )
}

@Composable
fun ProfileScreenContent(
    uiState: ProfileUiState,
    onNameChange: (String) -> Unit,
    onPickName: () -> Unit,
    onEmailChange: (String) -> Unit
) {
    Column {
        Text(text = "Current name:")
        TextField(value = uiState.name, onValueChange = onNameChange, singleLine = true, modifier = Modifier.border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
        Text(text = "Current email:")
        TextField(value = uiState.email, onValueChange = onEmailChange)
    }
}

@Preview
@Composable
fun ProfilePreview() {
    WeShareTheme {
    }
}
