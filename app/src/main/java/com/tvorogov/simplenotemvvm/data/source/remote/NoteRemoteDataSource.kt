package com.tvorogov.simplenotemvvm.data.source.remote

import com.tvorogov.simplenotemvvm.data.Note
import com.tvorogov.simplenotemvvm.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRemoteDataSource {

    fun getAllRemoteNotes(): Flow<Resource<List<Note>>> = flow {

    }

}