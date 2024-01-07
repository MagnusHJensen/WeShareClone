package dk.sdu.weshareclone.screens.profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun ProfileScreen(restartApp: (String) -> Unit, openScreen: (String) -> Unit, viewModel: ProfileViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    ProfileScreenContent(
        uiState = uiState,
        openScreen = openScreen,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onNameUpdate =  viewModel::onUpdateName,
        onEmailUpdate = viewModel::onUpdateEmail,
        onSignOutClick = {viewModel.onSignOutClick(restartApp) }
    )
}

@Composable
fun ProfileScreenContent(
    uiState: ProfileUiState,
    openScreen: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNameUpdate: () -> Unit,
    onEmailUpdate: () -> Unit,
    onSignOutClick: () -> Unit
) {
    val context = LocalContext.current
    val isNotificationsEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled()

    Column (Modifier.padding(25.dp)) {
        Text(text = "Current name:")
        TextField(value = uiState.name, onValueChange = onNameChange, singleLine = true, modifier = Modifier.border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
        Text(text = "Current email:")
        TextField(value = uiState.email, onValueChange = onEmailChange, enabled = false)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Is notifications enabled")
            Switch(checked = isNotificationsEnabled, onCheckedChange = {})            
        }


        Column {
            Button(onClick = onNameUpdate) {
                Text(text = "Update name")
            }
            TextButton(onClick = onSignOutClick) {
                Text(text = "Sign out", color = Color.Red)
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
