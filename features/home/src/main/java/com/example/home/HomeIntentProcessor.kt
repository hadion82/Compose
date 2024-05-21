package com.example.home

import com.example.shared.hanlder.EventHandler
import com.example.shared.hanlder.EffectHandler
import com.example.shared.processor.IntentProcessor
import timber.log.Timber
import javax.inject.Inject

interface HomeProcessor : IntentProcessor<Intention, Action>

class HomeIntentProcessor @Inject constructor(
    actionHandler: HomeEventHandler,
    effectHandler: HomeEffectHandler
) : HomeProcessor,
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