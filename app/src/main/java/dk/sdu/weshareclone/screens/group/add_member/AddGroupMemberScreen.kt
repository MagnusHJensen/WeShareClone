package dk.sdu.weshareclone.screens.group.add_member

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.components.InputField
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun AddGroupMemberScreen(popUp: () -> Unit, viewModel: AddGroupMemberViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState;
    val context = LocalContext.current;

    CreateGroupScreenContent(
        email = uiState.email,
        onEmailChange = viewModel::onEmailChange,
        onAddGroupMember = { viewModel.onAddGroupMember(popUp) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        }
    )
}

@Composable
fun CreateGroupScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    onAddGroupMember: () -> Unit
) {
    Column(Modifier.padding(25.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Add a group member", fontSize = TextUnit(5.0F, TextUnitType.Em))
        InputField(label = "Email of the person you want to invite", value = email, onValueChange = onEmailChange)
        Button(onClick = onAddGroupMember) {
            Text(text = "Add group member")
        }
    }
}

@Composable
@Preview
fun CreateGroupPreview() {
    WeShareTheme {
        CreateGroupScreenContent(
            email = "test@gmail.com",
            onEmailChange = {},
            onAddGroupMember = {}
        )
    }
}