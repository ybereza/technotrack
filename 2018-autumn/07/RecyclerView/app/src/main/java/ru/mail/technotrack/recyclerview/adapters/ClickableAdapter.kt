package ru.mail.technotrack.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.mail.technotrack.recyclerview.R

class ClickableAdapter : RecyclerView.Adapter<ClickableViewHolder>() {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ClickableViewHolder(
            inflater.inflate(R.layout.clickable_item, parent, false),
            ::onItemClick)
    }

    override fun getItemCount(): Int = arrayOfItems.size

    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {
        holder.setText(arrayOfItems[position])
        holder.setTitle(arrayOfItems[position].substring(0, 1).capitalize())
    }

    fun onItemClick(view: View, position: Int) {
        Toast.makeText(view.context, arrayOfItems[position], Toast.LENGTH_SHORT).show()
    }

}

class ClickableViewHolder(view : View,
                          private val clickListener : (View, Int) -> Unit ) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val text: TextView = view.findViewById(R.id.text)

    init {
        view.setOnClickListener {
            clickListener(it, adapterPosition)
        }
    }

    fun setText(text : String) {
        this.text.text = text
    }

    fun setTitle(title : String) {
        this.title.text = title
    }
}