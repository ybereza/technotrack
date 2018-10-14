package ru.mail.technotrack.mainui.list

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.mail.technotrack.mainui.R
import android.view.LayoutInflater

class ButtonsListAdapter(private val names : Array<String>, private val onClick : (Int) -> Unit) : RecyclerView.Adapter<ButtonHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ButtonHolder {
        val rootView = LayoutInflater.from(viewGroup.context).inflate(R.layout.button_layout, null, false)
        val button : Button = rootView.findViewById(R.id.button)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rootView.layoutParams = lp
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