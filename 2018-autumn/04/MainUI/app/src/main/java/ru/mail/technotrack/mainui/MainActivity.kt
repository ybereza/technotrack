package ru.mail.technotrack.mainui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import ru.mail.technotrack.mainui.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var router  : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Router(this, R.id.fragment_container)
        if (savedInstanceState == null) router.navigateTo(false, ::MainFragment)
    }

    override fun onBackPressed() {
        if (!router.navigateBack()) {
            super.onBackPressed()
        }
    }
}
