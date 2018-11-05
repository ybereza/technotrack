package ru.mail.technotrack.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mail.technotrack.recyclerview.R

class PageAdapter : RecyclerView.Adapter<PageViewHolder>() {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PageViewHolder(inflater.inflate(R.layout.page_layout, parent, false))
    }

    override fun getItemCount(): Int = arrayOfItems.size

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.setText(arrayOfItems[position])
    }
}

class PageViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val text: TextView = view.findViewById(R.id.text)

    fun setText(text : String) {
        this.text.text = text
    }

}