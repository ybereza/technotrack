package ru.mail.techotrack.lection9

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(private val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    val size: Long
        get() {
            val db = readableDatabase
            return DatabaseUtils.queryNumEntries(db, DT_NAME)
        }

    val allData: Cursor
        get() {
            val db = readableDatabase
            return db.query(DT_NAME, null, null, null, null, null, null)
        }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("create table " + DT_NAME + " ("
                + FN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FN_NAME + "  TEXT, "
                + FN_PHONE1 + "  TEXT, "
                + FN_PHONE2 + "  TEXT, "
                + FN_EMAIL + "  TEXT )")
        db.execSQL(query)
        // TODO: Load data from content provider
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO: Drop all tables
        db.execSQL("DROP TABLE IF EXISTS `conctacts`")
        onCreate(db)
    }

    fun addContact(contact: AppContact) {
        val db = writableDatabase!!
        val v = ContentValues()
        v.put(FN_NAME, contact.name)
        v.put(FN_PHONE1, contact.phone1)
        v.put(FN_PHONE2, contact.phone2)
        v.put(FN_EMAIL, contact.email)
        db.insert(DT_NAME, null, v)
    }

    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "contacts"

        private val DT_NAME = "contacts"
        private val FN_ID = "id"
        private val FN_NAME = "full_name"
        private val FN_PHONE1 = "phone1"
        private val FN_PHONE2 = "phone2"
        private val FN_EMAIL = "email"
    }

}
