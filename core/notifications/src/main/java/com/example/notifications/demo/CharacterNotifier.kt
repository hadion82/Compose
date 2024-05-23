package com.example.notifications.demo

import android.content.Context
import com.example.notifications.SystemTrayNotifier
import com.example.notifications.model.Channel
import com.example.notifications.model.Importance
import com.example.notifications.model.Summary
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) : SystemTrayNotifier(context) {

    companion object {
        private const val CHARACTER_NOTIFICATION_CHANNEL_ID = "CHARACTER_NOTIFICATION_CHANNEL_ID"
        private const val CHARACTER_NOTIFICATION_CHANNEL_NAME =
            "CHARACTER_NOTIFICATION_CHANNEL_NAME"

        private const val CHARACTER_NOTIFICATION_SUMMARY_ID = 1
        private const val CHARACTER_NOTIFICATION_SUMMARY_TITLE =
            "CHARACTER_NOTIFICATION_SUMMARY_TITLE"
        private const val CHARACTER_NOTIFICATION_GROUP =
            "CHARACTER_NOTIFICATION_GROUP"
    }

    override fun createChannel(): Channel =
        Channel(
            CHARACTER_NOTIFICATION_CHANNEL_ID,
            CHARACTER_NOTIFICATION_CHANNEL_NAME,
            Importance.High
        )

    override fun createSummary(): Summary =
        Summary(
            CHARACTER_NOTIFICATION_SUMMARY_ID,
            CHARACTER_NOTIFICATION_SUMMARY_TITLE,
            android.R.drawable.stat_notify_chat
        )

    override fun getGroup(): String = CHARACTER_NOTIFICATION_GROUP
}