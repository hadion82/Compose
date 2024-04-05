package com.example.compose.ui.main

import com.example.shared.hanlder.EventHandler
import com.example.shared.hanlder.EffectHandler
import com.example.shared.processor.IntentProcessor
import timber.log.Timber
import javax.inject.Inject

interface MainProcessor : IntentProcessor<Intention, Action>

class MainIntentProcessor @Inject constructor(
    actionHandler: MainEventHandler,
    effectHandler: MainEffectHandler
) : MainProcessor,
    EventHandler<Intention.Event> by actionHandler,
    EffectHandler<Intention.Effect> by effectHandler {

    override suspend fun process(intent: Intention) {
        Timber.d("intent -> $intent")
        when (intent) {
            is Intention.Event -> handleEvent(intent)
            is Intention.Effect -> handleEffect(intent)
        }
    }
}