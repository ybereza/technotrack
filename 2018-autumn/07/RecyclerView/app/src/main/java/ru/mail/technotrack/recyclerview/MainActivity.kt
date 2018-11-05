package ru.mail.technotrack.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mail.technotrack.recyclerview.fragments.MainFragment

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
