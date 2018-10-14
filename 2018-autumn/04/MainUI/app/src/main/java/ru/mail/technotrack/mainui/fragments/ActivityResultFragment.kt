package ru.mail.technotrack.mainui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.start_activity.*
import ru.mail.technotrack.mainui.CallableActivity
import ru.mail.technotrack.mainui.R
import ru.mail.technotrack.mainui.createIntent

class ActivityResultFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.start_activity, container, false)
        button.setOnClickListener {
            val i = requireContext().createIntent<CallableActivity>()
            i.putExtra("text", "Hello, Android!")
            startActivityForResult(i, 12345)
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 12345 && resultCode == Activity.RESULT_OK) {
            val text =  data?.getStringExtra("text")
            textview.text = text
        }
    }
}