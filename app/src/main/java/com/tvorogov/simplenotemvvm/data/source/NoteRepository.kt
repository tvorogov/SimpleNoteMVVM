package com.tvorogov.simplenotemvvm.data.source

import androidx.lifecycle.LiveData
import com.tvorogov.simplenotemvvm.data.Note
import com.tvorogov.simplenotemvvm.data.source.local.NoteDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)
}
