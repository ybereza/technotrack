package ru.mail.technotrack.mainui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.mail.technotrack.mainui.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var router  : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Router(this, R.id.fragment_container)
        router.navigateTo(false, ::MainFragment)
    }

    override fun onBackPressed() {
        if (!router.navigateBack()) {
            super.onBackPressed()
        }
    }
}
