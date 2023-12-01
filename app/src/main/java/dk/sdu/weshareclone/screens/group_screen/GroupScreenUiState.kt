package dk.sdu.weshareclone.screens.group_screen

import dk.sdu.weshareclone.model.Group
import dk.sdu.weshareclone.model.Profile

data class GroupScreenUiState(
    val group: Group,
    val groupMembers: List<Profile>
)