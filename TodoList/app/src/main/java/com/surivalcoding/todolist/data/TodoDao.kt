package com.surivalcoding.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY date DESC")
    fun getAll(): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE id = :id ORDER BY date DESC")
    fun getTodo(id: Long): Flow<Todo?>

    @Upsert
    suspend fun upsert(todo: Todo)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun delete(id: Long)
}