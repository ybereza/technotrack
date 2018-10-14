package ru.mail.technotrack.mainui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class CallableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.callable_activity)

        val text = intent.getStringExtra("text")
        findViewById<TextView>(R.id.textview).text = "I was called with text $text"

        val i = Intent()
        i.putExtra("text", "Text from Callable Activity")
        setResult(Activity.RESULT_OK, i)
    }
}