package ru.mipt.dbexample

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {
    private lateinit var dbHelper: DBHelper
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        dbHelper = DBHelper(this)
        val listView = findViewById<ListView>(R.id.list)
        listAdapter = ListAdapter(dbHelper)
        listView.adapter = listAdapter
        listView.onItemClickListener = this

        findViewById<View>(R.id.add).setOnClickListener(this)
        findViewById<View>(R.id.delete).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.add -> addNewValue()
            R.id.delete -> removeLastValue()
        }
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        editValue(i)
    }

    private fun removeLastValue() {
        dbHelper.removeLast()
        listAdapter.notifyDataSetChanged()
    }

    private fun addNewValue() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(resources.getString(R.string.enter_text))

        val l = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = l.inflate(R.layout.edit, null)
        val text = v.findViewById<View>(R.id.edit_text) as EditText
        alertDialog.setView(v)
        text.setText("Hello, World!")

        alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
            val value = text.text.toString()
            dbHelper.addValue(value)
            listAdapter.notifyDataSetChanged()
        }

        alertDialog.show()
    }

    private fun editValue(pos: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(resources.getString(R.string.enter_text))

        val l = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = l.inflate(R.layout.edit, null)
        val text = v.findViewById<View>(R.id.edit_text) as EditText
        alertDialog.setView(v)

        val value = dbHelper.getValue(pos + 1)
        if (value != null) {
            text.setText(value)
        }

        alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
            val value = text.text.toString()
            dbHelper.editValue(pos + 1, value)
            listAdapter.notifyDataSetChanged()
        }

        alertDialog.show()
    }
}
