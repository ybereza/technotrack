package ru.mail.technotrack.mainui

import android.app.Application
import ru.mail.technotrack.mainui.data.ResultFragmentState

class MainApplication : Application() {

    val fragmentStateHolder = ResultFragmentState("")

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MainApplication
            private set
    }
}