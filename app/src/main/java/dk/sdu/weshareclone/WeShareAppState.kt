package dk.sdu.weshareclone

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

@Stable
class WeShareAppState(
    val navController: NavHostController
) {

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        if (navController.currentDestination?.route == route) return
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        if (navController.currentDestination?.route == route) return
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = false }

        }
    }

    fun clearAndNavigate(route: String) {
        if (navController.currentDestination?.route == route) return

        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}