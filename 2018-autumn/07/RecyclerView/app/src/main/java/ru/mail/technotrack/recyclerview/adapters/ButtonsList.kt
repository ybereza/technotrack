package ru.mail.technotrack.recyclerview.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.view.LayoutInflater
import ru.mail.technotrack.recyclerview.R

class ButtonsListAdapter(private val names : Array<String>, private val onClick : (Int) -> Unit) : RecyclerView.Adapter<ButtonHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ButtonHolder {
        val rootView = LayoutInflater.from(viewGroup.context).inflate(R.layout.button_layout, viewGroup, false)
        val button : Button = rootView.findViewById(R.id.button)
        return ButtonHolder(rootView, button, onClick)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: ButtonHolder, pos: Int) {
        holder.button.text = names[pos]
    }

}

class ButtonHolder(view : View, val button : Button, val onClick : (Int) -> Unit) : RecyclerView.ViewHolder(view) {
    init {
        button.setOnClickListener {
            onClick(adapterPosition)
        }
    }
}