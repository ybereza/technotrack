package ru.mail.techotrack.lection14

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

fun getHelperActivity(context : Context, requestCode : Int) : PendingIntent {
    val notificationIntent = Intent(context, HelperActivity::class.java)
    notificationIntent.putExtra("notification", true)
    return PendingIntent.getActivity(context, requestCode, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)
}

class HelperActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        if (bundle!!.getBoolean("notification")) {
            MainActivity.messages.clear()
            val toast = Toast.makeText(applicationContext,
                    "Нажали на пуш!", Toast.LENGTH_SHORT)
            toast.show()
            Log.d(TAG, "onStart() called with: " + "notification")
        }
        if (bundle.getBoolean("todo")) {
            val toast = Toast.makeText(applicationContext,
                    "Нажали на действие в пуше!", Toast.LENGTH_SHORT)
            toast.show()
            Log.d(TAG, "onStart() called with: " + "notification")

        }
        finish()
    }

    companion object {
        private val TAG = "HelperActivity"
    }
}
