package dk.sdu.weshareclone

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dk.sdu.weshareclone.screens.home_screen.HomeScreen
import dk.sdu.weshareclone.screens.login.LoginSceen
import dk.sdu.weshareclone.screens.payment_screen.PaymentScreen
import dk.sdu.weshareclone.screens.pick_name.PickNameScreen
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun WeShareApp() {
    WeShareTheme {
        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()

            // TODO: Add tab bar
            NavHost(navController = appState.navController, startDestination = LOGIN_SCREEN) {
                weShareGraph(appState)
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
        LoginSceen(openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route, popUp)})
    }

    composable(HOME_SCREEN) {
        HomeScreen(restartApp = { route -> appState.clearAndNavigate(route) },openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route, popUp)})
    }

    composable(PICK_NAME_SCREEN) {
        PickNameScreen(openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route, popUp)})
    }
    
    composable(PAYMENT_SCREEN) {
        PaymentScreen(popUp = {appState.popUp()})
    }
}