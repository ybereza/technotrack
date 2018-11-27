package ru.mail.techotrack.lection8

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ScrollingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
    }

    override fun onStart() {
        super.onStart()
        val frag = supportFragmentManager.findFragmentById(R.id.main_fragment)
        if (frag == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.main_fragment, ListFragment())
                    .commit()
        }
    }
}
