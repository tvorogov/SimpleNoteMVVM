package com.tvorogov.simplenotemvvm.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tvorogov.simplenotemvvm.data.Note

@Database( entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun  noteDao(): NoteDao
}