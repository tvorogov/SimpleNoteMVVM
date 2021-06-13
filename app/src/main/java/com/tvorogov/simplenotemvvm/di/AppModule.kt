package com.tvorogov.simplenotemvvm.di

import android.app.Application
import androidx.room.Room
import com.tvorogov.simplenotemvvm.data.source.DefaultNoteRepository
import com.tvorogov.simplenotemvvm.data.source.NoteRepository
import com.tvorogov.simplenotemvvm.data.source.local.NoteDao
import com.tvorogov.simplenotemvvm.data.source.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, NoteDatabase::class.java, "note_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase) = db.noteDao()

    @Provides
    @Singleton
    fun provideRepository(dao: NoteDao): NoteRepository {
        return DefaultNoteRepository(dao)
    }


}