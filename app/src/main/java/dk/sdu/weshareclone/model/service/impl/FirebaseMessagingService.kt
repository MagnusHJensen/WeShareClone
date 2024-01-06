package dk.sdu.weshareclone.model.service.impl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Build
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dk.sdu.weshareclone.R
import dk.sdu.weshareclone.WeShareActivity
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            sendNotifcation(it)
        }
    }

    private fun sendNotifcation(message: RemoteMessage.Notification) {
        val intent = Intent(this, WeShareActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE)

        val channelId = "default_channel"
        val channelName = "Main notification channel"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val channel = NotificationChannel(channelId, channelName, IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val random: Random = Random.Default

        manager.notify(random.nextInt(), notificationBuilder.build())
    }
}