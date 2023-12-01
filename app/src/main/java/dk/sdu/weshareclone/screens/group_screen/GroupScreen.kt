package dk.sdu.weshareclone.screens.group_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.screens.payment_screen.PaymentContent

@Composable
fun GroupScreen(
    popUp: () -> Unit,
    viewModel: GroupScreenViewModel = hiltViewModel(),
    groupId: String?
) {
    val uiState by viewModel.uiState
    LaunchedEffect(
        true
    ) {
        groupId?.let { viewModel.fetchGroupId(groupId) }
    }

    GroupScreenContent(uiState = uiState)
}

@Composable
fun GroupScreenContent(
    uiState: Group,

    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = uiState.id)
    }
}