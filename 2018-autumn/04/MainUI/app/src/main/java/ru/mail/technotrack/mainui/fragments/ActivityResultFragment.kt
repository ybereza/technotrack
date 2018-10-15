package ru.mail.technotrack.mainui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.start_activity.*
import ru.mail.technotrack.mainui.CallableActivity
import ru.mail.technotrack.mainui.MainApplication
import ru.mail.technotrack.mainui.R
import ru.mail.technotrack.mainui.createIntent

class ActivityResultFragment : Fragment() {

    var defaultText = ""
    lateinit var textview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.run {
            //defaultText = MainApplication.instance.fragmentStateHolder.text
            defaultText = getString("SAVED_TEXT_STATE") ?: ""
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.start_activity, container, false)
        val button = root.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val i = requireContext().createIntent<CallableActivity>()
            i.putExtra("text", "Hello, Android!")
            startActivityForResult(i, 12345)
        }

        textview = root.findViewById(R.id.textview)
        textview.text = defaultText

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 12345 && resultCode == Activity.RESULT_OK) {
            defaultText =  data?.getStringExtra("text") ?: ""
            //MainApplication.instance.fragmentStateHolder.text = defaultText
            textview.text = defaultText
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("SAVED_TEXT_STATE", defaultText)
        super.onSaveInstanceState(outState)
    }
}