package com.example.todolist_mobileproject.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist_mobileproject.model.Item

class TodoDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "todo.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TodoContract.TodoEntry.TABLE_NAME} (" +
                    "${TodoContract.TodoEntry._ID} INTEGER PRIMARY KEY," +
                    "${TodoContract.TodoEntry.COLUMN_TITLE} TEXT," +
                    "${TodoContract.TodoEntry.COLUMN_DESCRIPTION} TEXT," +
                    "${TodoContract.TodoEntry.COLUMN_STATE} TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TodoContract.TodoEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun ingresoTarea(title: String, description: String, state: String): Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_TITLE, title)
            put(TodoContract.TodoEntry.COLUMN_DESCRIPTION, description)
            put(TodoContract.TodoEntry.COLUMN_STATE, state)
        }

        val newRowId = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)

        return newRowId
    }

    fun obtenerTareas(): MutableList<Item> {
        val items = mutableListOf<Item>()
        val db = readableDatabase

        val projection = arrayOf(
            TodoContract.TodoEntry._ID,
            TodoContract.TodoEntry.COLUMN_TITLE,
            TodoContract.TodoEntry.COLUMN_DESCRIPTION,
            TodoContract.TodoEntry.COLUMN_STATE
        )

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,  // Nombre de la tabla
            projection,                         // Columnas que deseamos consultar
            null,                               // Cláusula WHERE (sin condición, consultamos todo)
            null,                               // Valores para la cláusula WHERE
            null,                               // No agrupar las filas
            null,                               // No filtrar por grupos de filas
            "${TodoContract.TodoEntry._ID} DESC"                                // No ordenar las filas
        )

        cursor.use { // Use cursor as a lambda receiver
            while (cursor.moveToNext()) {
                val itemId = cursor.getLong(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry._ID))
                val itemTitle = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_TITLE))
                val itemDescription = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_DESCRIPTION))
                val itemState = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_STATE))

                items.add(Item(itemId, itemTitle, itemDescription, itemState))
            }
        }

        return items
    }

    fun actualizarTarea(item: Item) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_TITLE, item.title)
            put(TodoContract.TodoEntry.COLUMN_DESCRIPTION, item.description)
            put(TodoContract.TodoEntry.COLUMN_STATE, item.state)
        }

        val selection = "${TodoContract.TodoEntry._ID} = ?"
        val selectionArgs = arrayOf(item._ID.toString())

        db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }
}



