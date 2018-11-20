package ru.mipt.dbexample

import android.content.Context
import android.widget.BaseAdapter

class Model(context : Context, val roomModel : Boolean = false) {

    private val dbHelper = DBHelper(context)
    private val room = ValuesDatabase.getAppDataBase(context)
    private lateinit var listAdapter : ListAdapter
    private var values : List<Value> = emptyList()

    fun createAdapter() : BaseAdapter {
        listAdapter = ListAdapter(this)
        return listAdapter
    }

    fun size() : Long {
        if (roomModel) {
            values = room?.valuesDao()?.getValues() ?: emptyList()
            return values.size.toLong()
        }
        return dbHelper.count
    }

    fun text(pos : Int) : String? {
        if (roomModel) {
            return values[pos].text
        }
        return dbHelper.getValue(pos + 1)
    }

    fun editValue(pos : Int, newText : String) {
        if (roomModel) {
            val newValue = values[pos].copy(text = newText)
            room?.valuesDao()?.updateValue(newValue)
        }
        else {
            dbHelper.editValue(pos + 1, newText)
        }
        listAdapter.notifyDataSetChanged()
    }

    fun addValue(value : String) {
        if (roomModel) {
            room?.valuesDao()?.insertValue(Value(0, value))
        }
        else {
            dbHelper.addValue(value)
        }
        listAdapter.notifyDataSetChanged()
    }

    fun removeLast() {
        if (roomModel) {
            room?.valuesDao()?.deleteValue(values.last())
        }
        else {
            dbHelper.removeLast()
        }
        listAdapter.notifyDataSetChanged()
    }
}