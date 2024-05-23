package com.example.notifications

import com.example.notifications.model.Summary

abstract class SummaryNotifier: ChannelNotifier() {

    abstract fun createSummary(): Summary
}