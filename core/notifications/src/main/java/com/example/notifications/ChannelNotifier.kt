package com.example.notifications

import com.example.notifications.model.Channel

abstract class ChannelNotifier: Notifier {
    abstract fun createChannel(): Channel
}