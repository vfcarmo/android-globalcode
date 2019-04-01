import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.pushnotification.R
import com.example.pushnotification.domain.LoadImageType
import com.example.pushnotification.service.LoadImageStrategy
import com.example.pushnotification.view.MainActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object NotificationCreation {
    private const val NOTIFY_ID = 1000
    private val vibration = longArrayOf(300, 400, 500, 400, 300)

    private var notificationManager: NotificationManager? = null

    private const val CHANNEL_ID = "KotlinPush_1"
    private const val CHANNEL_NAME = "Kotlin - Push Channel 1"
    private const val CHANNEL_DESCRIPTION = "Kotlin - Push Channel Description"

    fun create(context: Context, title: String, body: String,
               imageLoading: Boolean, includeActions: Boolean = false) {
        create(context, title, body, imageLoading, null, includeActions)
    }

    fun create(context: Context, title: String, body: String, imageLoading: Boolean,
               imageUrl: String, loadImageType: LoadImageType, includeActions: Boolean = false) {
        doAsync {
            val bitmap = LoadImageStrategy.loadImage(context, loadImageType, imageUrl)

            uiThread {
                create(context, title, body, imageLoading, bitmap, includeActions)
            }
        }
    }

    private fun create(context: Context, title: String, body: String, imageLoading: Boolean,
                       bitmap: Bitmap? = null, includeActions: Boolean = false) {
        if (notificationManager == null) {
            notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        notificationManager?.let { notificationManager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = notificationManager.getNotificationChannel(CHANNEL_ID)
                if (channel == null) {
                    val newChannel = NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    newChannel.description = CHANNEL_DESCRIPTION
                    newChannel.enableVibration(true)
                    newChannel.enableLights(true)
                    newChannel.vibrationPattern = vibration

                    notificationManager.createNotificationChannel(newChannel)
                }
            }

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val pedingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentIntent(pedingIntent)
                .setTicker(title)
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setOnlyAlertOnce(true)

            if (imageLoading) {
                builder.setProgress(100, 0, true)
            } else {
                builder.setProgress(0, 0, false)
            }

            if (bitmap != null) {
                builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            }

            if (includeActions) {
                val intentAction1 = Intent(context, MainActivity::class.java)
                intentAction1.action = context.getString(R.string.label_disliked)
                val pendingIntentAction1 = PendingIntent.getActivity(context, 0, intentAction1, 0)
                val action1 = NotificationCompat.Action(android.R.drawable.arrow_down_float,
                    context.getString(R.string.label_disliked),
                    pendingIntentAction1)

                val intentAction2 = Intent(context, MainActivity::class.java)
                intentAction2.action = context.getString(R.string.label_liked)
                val pendingIntentAction2 = PendingIntent.getActivity(context, 0, intentAction2, 0)
                val action2 = NotificationCompat.Action(
                    android.R.drawable.arrow_up_float,
                    context.getString(R.string.label_liked),
                    pendingIntentAction2)

                builder
                    .addAction(action1)
                    .addAction(action2)
            }

            val notification = builder.build()

            notificationManager.notify(NOTIFY_ID, notification)
        }
    }
}