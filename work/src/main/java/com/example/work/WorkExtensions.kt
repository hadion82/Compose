package com.example.work

import androidx.work.WorkInfo

internal fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }