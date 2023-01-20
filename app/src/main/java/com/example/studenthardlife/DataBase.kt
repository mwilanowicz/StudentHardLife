package com.example.studenthardlife


import android.provider.BaseColumns

object TableInfo : BaseColumns {
    const val tasks = "Tasks"
    const val title = "Title"
    const val description = "Description"
}

object BasicCommand {
    const val SQL_CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS ${TableInfo.tasks} (" +
                "id INTEGER PRIMARY KEY, " +
                "${TableInfo.title} TEXT NOT NULL, " +
                "${TableInfo.description} TEXT NOT NULL)"
}

