package dk.sdu.weshareclone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.ManageAccounts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.sdu.weshareclone.screens.create_expense_screen.CreateExpenseScreen
import dk.sdu.weshareclone.screens.group.add_member.AddGroupMemberScreen
import dk.sdu.weshareclone.screens.group.create_group.CreateGroupScreen
import dk.sdu.weshareclone.screens.group.group_details.GroupScreen
import dk.sdu.weshareclone.screens.group.view_expense.ViewExpenseScreen
import dk.sdu.weshareclone.screens.home_screen.HomeScreen
import dk.sdu.weshareclone.screens.login.LoginSceen
import dk.sdu.weshareclone.screens.payment_screen.PaymentScreen
import dk.sdu.weshareclone.screens.pick_name.PickNameScreen
import dk.sdu.weshareclone.screens.profile_screen.ProfileScreen
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun WeShareApp() {


    WeShareTheme {
        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()
            val bottomBarState = rememberSaveable { mutableStateOf(true) }

            when (appState.navController.currentBackStackEntryAsState().value?.destination?.route) {
                LOGIN_SCREEN -> bottomBarState.value = false
                PICK_NAME_SCREEN -> bottomBarState.value = false
                else -> bottomBarState.value = true
            }

            Scaffold(
                bottomBar = {
                    if (bottomBarState.value) {
                        BottomAppBar {
                            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp, 0.dp)) {
                                IconButton(onClick = { appState.clearAndNavigate(HOME_SCREEN) }) {
                                    Icon(Icons.Rounded.Groups, contentDescription = null)
                                }
                                IconButton(onClick = { appState.clearAndNavigate(PROFILE_SCREEN) }) {
                                    Icon(Icons.Rounded.ManageAccounts, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier.padding(
                        PaddingValues(
                            0.dp,
                            0.dp,
                            0.dp,
                            innerPadding.calculateBottomPadding()
                        )
                    )
                ) {
                    NavHost(
                        navController = appState.navController,
                        startDestination = LOGIN_SCREEN
                    ) {
                        weShareGraph(appState)
                    }
                }
            }

        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) =
    remember(navController) {
        WeShareAppState(navController)
    }

fun NavGraphBuilder.weShareGraph(appState: WeShareAppState) {
    composable(LOGIN_SCREEN) {
        LoginSceen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(HOME_SCREEN) {
        HomeScreen(
            openScreen = { route -> appState.navigate(route) },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp)})
    }

    composable(PICK_NAME_SCREEN) {
        PickNameScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(PAYMENT_SCREEN) {
        PaymentScreen(popUp = { appState.popUp() })
    }

    composable(
        route = "$GROUP_SCREEN$GROUP_ID_ARG",
        arguments = listOf(navArgument(GROUP_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        GroupScreen(popUp = { appState.popUp() }, openScreen = { route -> appState.navigate(route)}, openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp)})
    }

    composable(CREATE_GROUP_SCREEN) {
        CreateGroupScreen(popUp = { appState.popUp() })
    }
    
    composable(
        route = "$ADD_GROUP_MEMBER_SCREEN$GROUP_ID_ARG",
        arguments = listOf(navArgument(GROUP_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        AddGroupMemberScreen(popUp = { appState.popUp() })
    }

    composable(PROFILE_SCREEN) {
        ProfileScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = "$CREATE_EXPENSE_SCREEN$GROUP_ID_ARG",
        arguments = listOf(navArgument(GROUP_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        CreateExpenseScreen(popUp = { appState.popUp() })
    }

    composable(
        route = "$VIEW_EXPENSE_SCREEN$EXPENSE_ID_ARG",
        arguments = listOf(navArgument(EXPENSE_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        ViewExpenseScreen()
    }
}