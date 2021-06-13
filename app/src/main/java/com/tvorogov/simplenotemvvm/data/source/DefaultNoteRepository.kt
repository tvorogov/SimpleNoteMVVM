package com.tvorogov.simplenotemvvm.data.source

import androidx.lifecycle.LiveData
import com.tvorogov.simplenotemvvm.data.Note
import com.tvorogov.simplenotemvvm.data.source.NoteRepository
import com.tvorogov.simplenotemvvm.data.source.local.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


class DefaultNoteRepository @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insert(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }

}