package dk.sdu.weshareclone.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.functions.functions

object NotificationSenderService {
    private val functions = Firebase.functions

    fun sendFCMessage(token: String, title: String, message: String) {
        val data = hashMapOf(
            "title" to title,
            "body" to message,
            "tokens" to listOf(token)
        )

        functions.getHttpsCallable("sendNotification").call(data) // Default name from firebase function.
    }
}