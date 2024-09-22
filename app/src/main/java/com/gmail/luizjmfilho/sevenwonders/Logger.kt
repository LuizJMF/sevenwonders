package com.gmail.luizjmfilho.sevenwonders

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class Logger @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics,
) {
    fun exception(ex: Throwable) {
        firebaseCrashlytics.recordException(ex)
    }
}