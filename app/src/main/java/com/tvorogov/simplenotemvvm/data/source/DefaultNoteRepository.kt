package com.tvorogov.simplenotemvvm.data.source

import androidx.lifecycle.LiveData
import com.tvorogov.simplenotemvvm.data.Note
import com.tvorogov.simplenotemvvm.data.source.NoteRepository
import com.tvorogov.simplenotemvvm.data.source.local.NoteDao
import com.tvorogov.simplenotemvvm.data.source.local.NoteLocalDataSource
import com.tvorogov.simplenotemvvm.data.source.remote.NoteRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


class DefaultNoteRepository @Inject constructor(
    private val noteLocalDataSource: NoteLocalDataSource,
    private val noteRemoteDataSource: NoteRemoteDataSource,
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
       return noteLocalDataSource.getAllNotes()
    }

    override suspend fun insertNote(note: Note) {
        noteLocalDataSource.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteLocalDataSource.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteLocalDataSource.deleteNote(note)
    }

}