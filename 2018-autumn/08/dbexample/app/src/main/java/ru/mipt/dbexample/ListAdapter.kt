package ru.mipt.dbexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(private val model: Model) : BaseAdapter() {
    override fun getCount(): Int {
        return model.size().toInt()
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        var convertedView = view
        if (convertedView == null) {
            convertedView = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.list_item, viewGroup, false)
        }

        val text = model.text(i)
        val textView = convertedView?.findViewById<TextView>(R.id.text)
        textView?.text = text ?: ""

        return convertedView
    }
}
