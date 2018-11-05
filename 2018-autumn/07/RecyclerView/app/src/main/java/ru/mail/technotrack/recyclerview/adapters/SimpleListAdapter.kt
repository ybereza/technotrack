package ru.mail.technotrack.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mail.technotrack.recyclerview.R

class SimpleListAdapter : RecyclerView.Adapter<SimpleViewHolder>() {

    val arrayOfItems = arrayOf(
        "lorem",
        "ipsum",
        "dolor",
        "sit",
        "amet",
        "consectetuer",
        "adipiscing",
        "elit",
        "morbi",
        "vel",
        "ligula",
        "vitae",
        "arcu",
        "aliquet",
        "mollis",
        "etiam",
        "vel",
        "erat",
        "placerat",
        "ante",
        "porttitor",
        "sodales",
        "pellentesque",
        "augue",
        "purus"
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SimpleViewHolder(inflater.inflate(R.layout.simple_item, parent, false))
    }

    override fun getItemCount(): Int = arrayOfItems.size

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.setText(arrayOfItems[position])
        holder.setTitle(arrayOfItems[position].substring(0, 1).capitalize())
    }
}

class SimpleViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val text: TextView = view.findViewById(R.id.text)

    fun setText(text : String) {
        this.text.text = text
    }

    fun setTitle(title : String) {
        this.title.text = title
    }
}