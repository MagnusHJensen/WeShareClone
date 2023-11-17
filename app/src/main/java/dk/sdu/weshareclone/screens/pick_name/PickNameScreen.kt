package dk.sdu.weshareclone.screens.pick_name

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun PickNameScreen(openAndPopUp: (String, String) -> Unit, viewModel: PickNameViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    PickNameContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onPickName = { viewModel.onPickName(openAndPopUp)}
    )

}

@Composable
fun PickNameContent(
    uiState: PickNameUiState,
    onNameChange: (String) -> Unit,
    onPickName: () -> Unit
) {
    Column {
        Text(text = "Pick your name")
        TextField(value = uiState.name, onValueChange = onNameChange, singleLine = true, modifier = Modifier.border(border = BorderStroke(width = Dp(1.0F), color = Color.Black)))
        Button(onClick = onPickName, enabled = uiState.name.isNotEmpty()) {
            Text(text = "Pick name")
        }
    }
}