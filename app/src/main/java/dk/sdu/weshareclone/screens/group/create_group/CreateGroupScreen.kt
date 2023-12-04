package dk.sdu.weshareclone.screens.group.create_group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.components.InputField
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun CreateGroupScreen(popUp: () -> Unit, viewModel: CreateGroupViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState;

    CreateGroupScreenContent(
        name = uiState.name,
        onNameChange = viewModel::onNameChange,
        description = uiState.description,
        onDescriptionChange = viewModel::onDescriptionChange,
        onCreateGroupClick = { viewModel.onCreateGroupClick(popUp) }
    )
}

@Composable
fun CreateGroupScreenContent(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onCreateGroupClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Create a new group", fontSize = TextUnit(5.0F, TextUnitType.Em))
        InputField(label = "Group name", value = name, onValueChange = onNameChange)
        InputField(
            label = "Group description",
            value = description,
            onValueChange = onDescriptionChange
        )
        Button(onClick = onCreateGroupClick, modifier = Modifier.align(End)) {
            Text(text = "Create group")
        }
    }
}

@Composable
@Preview
fun CreateGroupPreview() {
    WeShareTheme {
        CreateGroupScreenContent(
            name = "Boys",
            onNameChange = {},
            description = "This is the boys",
            onDescriptionChange = {},
            onCreateGroupClick = {}
        )
    }
}