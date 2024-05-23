package com.example.notifications

import com.example.notifications.model.Resource

interface Notifier {
    fun postNotifications(vararg newResources: Resource)
}