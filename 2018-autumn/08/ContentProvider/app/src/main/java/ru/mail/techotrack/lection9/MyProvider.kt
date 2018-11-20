package ru.mail.techotrack.lection9

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.text.TextUtils
import android.util.Log

/**
 * Created by vlad on 14/04/16.
 */
class MyProvider : ContentProvider() {

    private val LOG_TAG = "myLogs"

    private lateinit var dbHelper: DBHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(): Boolean {
        Log.d(LOG_TAG, "onCreate")
        dbHelper = DBHelper(context)
        return true
    }

    // чтение
    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        var selection = selection
        var sortOrder = sortOrder
        Log.d(LOG_TAG, "query, " + uri.toString())
        // проверяем Uri
        when (uriMatcher.match(uri)) {
            URI_CONTACTS // общий Uri
            -> {
                Log.d(LOG_TAG, "URI_CONTACTS")
                // если сортировка не указана, ставим свою - по имени
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "$CONTACT_NAME ASC"
                }
            }
            URI_CONTACTS_ID // Uri с ID
            -> {
                val id = uri.lastPathSegment
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id!!)
                // добавляем ID к условию выборки
                selection = if (TextUtils.isEmpty(selection)) {
                    "$CONTACT_ID = $id"
                } else {
                    "$selection AND $CONTACT_ID = $id"
                }
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cursor = db.query(CONTACT_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder)
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(context!!.contentResolver,
                CONTACT_CONTENT_URI)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(LOG_TAG, "insert, " + uri.toString())
        if (uriMatcher.match(uri) != URI_CONTACTS)
            throw IllegalArgumentException("Wrong URI: $uri")

        db = dbHelper.writableDatabase
        val rowID = db.insert(CONTACT_TABLE, null, values)
        val resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowID)
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        context!!.contentResolver.notifyChange(resultUri, null)
        return resultUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var selection = selection
        Log.d(LOG_TAG, "delete, " + uri.toString())
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> Log.d(LOG_TAG, "URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id!!)
                if (TextUtils.isEmpty(selection)) {
                    selection = "$CONTACT_ID = $id"
                } else {
                    selection = "$selection AND $CONTACT_ID = $id"
                }
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cnt = db.delete(CONTACT_TABLE, selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return cnt
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        var selection = selection
        Log.d(LOG_TAG, "update, " + uri.toString())
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> Log.d(LOG_TAG, "URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id!!)
                if (TextUtils.isEmpty(selection)) {
                    selection = "$CONTACT_ID = $id"
                } else {
                    selection = "$selection AND $CONTACT_ID = $id"
                }
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cnt = db.update(CONTACT_TABLE, values, selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return cnt
    }

    override fun getType(uri: Uri): String? {
        Log.d(LOG_TAG, "getType, " + uri.toString())
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> return CONTACT_CONTENT_TYPE
            URI_CONTACTS_ID -> return CONTACT_CONTENT_ITEM_TYPE
        }
        return null
    }

    private inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DB_CREATE)
            val cv = ContentValues()
            for (i in 1..3) {
                cv.put(CONTACT_NAME, "name $i")
                cv.put(CONTACT_EMAIL, "email $i")
                db.insert(CONTACT_TABLE, null, cv)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    }

    companion object {

        // // Константы для БД
        // БД
        internal val DB_NAME = "mydb"
        internal val DB_VERSION = 1

        // Таблица
        internal val CONTACT_TABLE = "contacts"

        // Поля
        internal val CONTACT_ID = "_id"
        internal val CONTACT_NAME = "name"
        internal val CONTACT_EMAIL = "email"

        // Скрипт создания таблицы
        internal val DB_CREATE = ("create table " + CONTACT_TABLE + "("
                + CONTACT_ID + " integer primary key autoincrement, "
                + CONTACT_NAME + " text, " + CONTACT_EMAIL + " text" + ");")

        // // Uri
        // authority
        internal val AUTHORITY = "ru.mail.techotrack.lection9.AdressBook"

        // path
        internal val CONTACT_PATH = "contacts"

        // Общий Uri
        val CONTACT_CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/" + CONTACT_PATH)

        // Типы данных
        // набор строк
        internal val CONTACT_CONTENT_TYPE = ("vnd.android.cursor.dir/vnd."
                + AUTHORITY + "." + CONTACT_PATH)

        // одна строка
        internal val CONTACT_CONTENT_ITEM_TYPE = ("vnd.android.cursor.item/vnd."
                + AUTHORITY + "." + CONTACT_PATH)

        //// UriMatcher
        // общий Uri
        internal val URI_CONTACTS = 1

        // Uri с указанным ID
        internal val URI_CONTACTS_ID = 2

        // описание и создание UriMatcher
        private val uriMatcher: UriMatcher

        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(AUTHORITY, CONTACT_PATH, URI_CONTACTS)
            uriMatcher.addURI(AUTHORITY, "$CONTACT_PATH/#", URI_CONTACTS_ID)
        }
    }
}
