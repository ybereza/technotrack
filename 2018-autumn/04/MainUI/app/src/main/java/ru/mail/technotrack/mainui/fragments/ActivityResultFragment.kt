package ru.mail.technotrack.mainui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ru.mail.technotrack.mainui.CallableActivity
import ru.mail.technotrack.mainui.R

class ActivityResultFragment : Fragment() {

    lateinit var textView : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.start_activity, null)
        root.findViewById<Button>(R.id.button).setOnClickListener {
            val i = Intent(activity, CallableActivity::class.java)
            i.putExtra("text", "Hello, Android!")
            startActivityForResult(i, 12345)
        }
        textView = root.findViewById(R.id.textview)

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 12345 && resultCode == Activity.RESULT_OK) {
            val text =  data?.getStringExtra("text")
            textView.text = text
        }
    }
}