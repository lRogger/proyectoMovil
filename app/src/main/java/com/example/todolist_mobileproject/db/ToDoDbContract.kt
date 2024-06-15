package com.example.todolist_mobileproject.db

import android.provider.BaseColumns

object TodoContract {

    object TodoEntry : BaseColumns {
        const val _ID = BaseColumns._ID
        const val TABLE_NAME = "tasks"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_STATE = "state"
    }
}
