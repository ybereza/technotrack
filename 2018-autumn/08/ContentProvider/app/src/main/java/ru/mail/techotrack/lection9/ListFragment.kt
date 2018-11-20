package ru.mail.techotrack.lection9

import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle

import androidx.cursoradapter.widget.SimpleCursorAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat

import android.widget.Button
import android.widget.ListView

/**
 * Created by vlad on 14/04/16.
 */
class ListFragment : Fragment() {

    internal val LOG_TAG = "myLogs"

    internal val CONTACT_URI = Uri
            .parse("content://ru.mail.techotrack.lection9.AdressBook/contacts")

    internal val CONTACT_NAME = "name"
    internal val CONTACT_EMAIL = "email"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.list_fragment, container, false) ?: return null

        val cursor = requireActivity().contentResolver.query(CONTACT_URI, null, null, null, null)
        activity!!.startManagingCursor(cursor)

        val from = arrayOf("name", "email")
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        val adapter = SimpleCursorAdapter(context!!, android.R.layout.simple_list_item_2, cursor, from, to)

        val lvContact = root.findViewById<View>(R.id.lvContact) as ListView
        lvContact.adapter = adapter

        val insert = root.findViewById<View>(R.id.btnInsert) as Button
        insert.setOnClickListener {
            val cv = ContentValues()
            cv.put(CONTACT_NAME, "name 4")
            cv.put(CONTACT_EMAIL, "email 4")
            val newUri = requireActivity().contentResolver.insert(CONTACT_URI, cv)
            Log.d(LOG_TAG, "insert, result Uri : " + newUri!!.toString())
        }

        val update = root.findViewById<View>(R.id.btnUpdate) as Button
        update.setOnClickListener {
            val cv = ContentValues()
            cv.put(CONTACT_NAME, "name 5")
            cv.put(CONTACT_EMAIL, "email 5")
            val uri = ContentUris.withAppendedId(CONTACT_URI, 2)
            val cnt = requireActivity().contentResolver.update(uri, cv, null, null)
            Log.d(LOG_TAG, "update, count = $cnt")
        }

        val delete = root.findViewById<View>(R.id.btnDelete) as Button
        delete.setOnClickListener {
            val uri = ContentUris.withAppendedId(CONTACT_URI, 3)
            val cnt = requireActivity().contentResolver.delete(uri, null, null)
            Log.d(LOG_TAG, "delete, count = $cnt")
        }

        val error = root.findViewById<View>(R.id.btnError) as Button
        error.setOnClickListener {
            val uri = Uri.parse("content://ru.mail.techotrack.lection9.AdressBook/phones")
            try {
                requireActivity().contentResolver.query(uri, null, null, null, null)
            } catch (ex: Exception) {
                Log.d(LOG_TAG, "Error: " + ex.javaClass + ", " + ex.message)
            }
        }
        return root

    }
}
