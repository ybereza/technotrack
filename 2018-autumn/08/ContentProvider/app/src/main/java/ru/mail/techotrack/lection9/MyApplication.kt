package ru.mail.techotrack.lection9

import android.app.Application

/**
 * Created by vlad on 14/04/16.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        db = DBHelper(applicationContext)
    }

    companion object {

        @get:Synchronized
        var db: DBHelper? = null
            private set
    }
}
