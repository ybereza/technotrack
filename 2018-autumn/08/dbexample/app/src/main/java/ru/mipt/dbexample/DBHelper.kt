package ru.mipt.dbexample

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteCursorDriver
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQuery

class DBHelper(context: Context,
               name: String = DB_NAME,
               factory: SQLiteDatabase.CursorFactory = Factory(),
               version: Int = DB_VER) : SQLiteOpenHelper(context, name, factory, version) {

    val count: Long
        get() {
            val db = readableDatabase
            val regionQuery = "select Count(*) as count from test"
            var result : Long = 0
            db?.use {
                val cur = it.rawQuery(regionQuery, arrayOf<String>())
                cur?.use {
                    it.moveToFirst()
                    result = it.getLong(it.getColumnIndexOrThrow("count"))
                }
            }
            return result
        }

    private class Factory : SQLiteDatabase.CursorFactory {
        override fun newCursor(sqLiteDatabase: SQLiteDatabase, sqLiteCursorDriver: SQLiteCursorDriver, s: String?, sqLiteQuery: SQLiteQuery): Cursor {
            return SQLiteCursor(sqLiteCursorDriver, s, sqLiteQuery)
        }
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, j: Int) {

    }

    fun addValue(value: String) {
        val db = writableDatabase
        db?.use {
            val v = ContentValues()
            v.put("value", value)
            it.insert("test", null, v)
        }
    }

    fun editValue(pos: Int, value: String) {
        val db = writableDatabase
        db?.use {
            val v = ContentValues()
            v.put("value", value)
            it.update("test", v, "id = ?", arrayOf(pos.toString()))
        }
    }

    fun getValue(pos: Int): String? {
        val db = readableDatabase
        var value: String? = null
        db?.use {
            val cur = it.query("test", arrayOf("id", "value"), "id=?", arrayOf(pos.toString()), null, null, null)
            cur?.use {
                it.moveToFirst()
                if (it.count > 0) {
                    value = it.getString(cur.getColumnIndexOrThrow("value"))
                }
            }
        }
        return value
    }

    fun removeLast() {
        val size = count
        if (size >= 0) {
            val db = writableDatabase
            db.use {
                it.delete("test", "id=?", arrayOf(size.toString()))
            }
        }
    }

    companion object {
        val DB_NAME = "values"
        val DB_VER = 1

        private val CREATE_DB = "CREATE TABLE test (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, value TEXT NOT NULL);"
    }
}
