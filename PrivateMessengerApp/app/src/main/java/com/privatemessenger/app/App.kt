package com.privatemessenger.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MMKV.initialize(this)
        Firebase.crashlytics.isCrashlyticsCollectionEnabled = true
    }
}