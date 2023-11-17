package dk.sdu.weshareclone.route

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login")
    object HomeScreen : Screen("home")
}