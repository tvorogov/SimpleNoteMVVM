package com.tvorogov.simplenotemvvm.data.source.local

import com.tvorogov.simplenotemvvm.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(
    private val noteDao: NoteDao
) {

    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    suspend fun insertNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }


}