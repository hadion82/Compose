package com.example.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notifications.model.Channel
import com.example.notifications.model.Resource

abstract class SystemTrayNotifier (
    private val context: Context
) : SummaryNotifier() {

    companion object {
        private const val MAX_NUM_SUMMARY_NOTIFICATIONS = 5
    }

    override fun postNotifications(vararg newResources: Resource) =
        with(context) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            val notificationRequests = newResources.map { newResource ->
                newResource.id to createNewsNotification {
                    setSmallIcon(newResource.smallIcon)
                        .setContentTitle(newResource.title)
                        .setContentText(newResource.content)
                        .setContentIntent(newsPendingIntent(newResource))
                        .setAutoCancel(newResource.autoCancel)
                        .setPriority(newResource.priority)
                        .setGroup(getGroup())
                }
            }
            val truncatedNotifications = newResources.take(MAX_NUM_SUMMARY_NOTIFICATIONS)

            val summary = createSummary()
            val summaryNotification = createNewsNotification {
                setContentTitle(summary.title)
                    .setSmallIcon(summary.smallIcon)
                    .setStyle(newsNotificationStyle(truncatedNotifications, summary.title))
                    .setGroup(getGroup())
                    .setGroupSummary(true)
                    .setAutoCancel(true)
                    .build()
            }

            val notificationManager = NotificationManagerCompat.from(this)
            notificationRequests.forEach { (id, notification) ->
                notificationManager.notify(id, notification)
            }
            notificationManager.notify(summary.id, summaryNotification)
        }

    private fun newsNotificationStyle(
        newResources: List<Resource>,
        title: String,
    ): NotificationCompat.InboxStyle = newResources
        .fold(NotificationCompat.InboxStyle()) { inboxStyle, newsResource ->
            inboxStyle.addLine(
                newsResource.title
            )
        }
        .setBigContentTitle(title)
        .setSummaryText(title)

    private fun Context.createNewsNotification(
        block: NotificationCompat.Builder.() -> Unit,
    ): Notification {
        val channel = createChannel()
        ensureNotificationChannelExists(channel)
        return NotificationCompat.Builder(
            this,
            channel.id,
        )
            .apply(block)
            .build()
    }

    abstract fun getGroup(): String
}

private fun Context.ensureNotificationChannelExists(channelResource: Channel) {
    val channel = NotificationChannel(
        channelResource.id,
        channelResource.name,
        channelResource.importance.value,
    ).apply {
        description = channelResource.description
    }
    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

private fun Context.newsPendingIntent(
    newResource: Resource,
): PendingIntent? = PendingIntent.getActivity(
    this,
    newResource.id,
    Intent().apply {
        action = Intent.ACTION_VIEW
        data = newResource.intent.uri
        component = ComponentName(
            packageName,
            newResource.intent.clazz.name,
        )
    },
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
)