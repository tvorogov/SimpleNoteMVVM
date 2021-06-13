package com.tvorogov.simplenotemvvm.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tvorogov.simplenotemvvm.data.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {


    @Query("SELECT * FROM note_table")
    fun getNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}