package com.gmail.luizjmfilho.sevenwonders

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun firebaseAnalytics(): FirebaseAnalytics {
        return Firebase.analytics
    }

    @Provides
    fun firebaseCrashlytics(): FirebaseCrashlytics {
        return Firebase.crashlytics
    }
}