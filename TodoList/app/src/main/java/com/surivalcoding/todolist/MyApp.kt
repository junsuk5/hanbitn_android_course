package com.surivalcoding.todolist

import android.app.Application
import androidx.room.Room
import com.surivalcoding.todolist.data.TodoDatabase

class MyApp : Application() {

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo-list",
        ).build()
    }
}