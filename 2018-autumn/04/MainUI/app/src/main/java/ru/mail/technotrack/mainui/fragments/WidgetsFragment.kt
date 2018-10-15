package ru.mail.technotrack.mainui.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import ru.mail.technotrack.mainui.R
import java.util.concurrent.TimeUnit

class WidgetsFragment : Fragment() {
    val timer = object : CountDownTimer(TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(1)) {
        override fun onFinish() {
            progressBar.progress = 0
            start()
        }

        override fun onTick(value: Long) {
            progressBar.progress += 10
        }
    }

    lateinit var progressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.widgets, container, false)
        progressBar = root.findViewById(R.id.progressBar)

        return root
    }

    override fun onStart() {
        timer.start()
        super.onStart()
    }

    override fun onStop() {
        timer.cancel()
        super.onStop()
    }
}