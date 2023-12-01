package dk.sdu.weshareclone

const val LOGIN_SCREEN = "LoginScreen"
const val PICK_NAME_SCREEN = "PickNameScreen"
const val HOME_SCREEN = "HomeScreen"
const val PAYMENT_SCREEN = "PaymentScreen"
const val GROUP_SCREEN = "GroupScreen/{groupId}"

fun replaceParameter(route: String, parameter: String, value: String): String {
    return route.replace("{$parameter}", value)
}