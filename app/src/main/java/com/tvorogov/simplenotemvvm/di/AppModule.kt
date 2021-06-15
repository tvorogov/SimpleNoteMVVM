package com.tvorogov.simplenotemvvm.di

import android.app.Application
import androidx.room.Room
import com.tvorogov.simplenotemvvm.data.source.DefaultNoteRepository
import com.tvorogov.simplenotemvvm.data.source.NoteRepository
import com.tvorogov.simplenotemvvm.data.source.local.NoteDao
import com.tvorogov.simplenotemvvm.data.source.local.NoteDatabase
import com.tvorogov.simplenotemvvm.data.source.local.NoteLocalDataSource
import com.tvorogov.simplenotemvvm.data.source.remote.NoteRemoteDataSource
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
    ): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, "note_database")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteLocalDataSource(dao: NoteDao): NoteLocalDataSource {
        return NoteLocalDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideNoteRemoteDataSource(): NoteRemoteDataSource {
        return NoteRemoteDataSource()
    }

    @Provides
    @Singleton
    fun provideRepository(
        noteLocalDataSource: NoteLocalDataSource,
        noteRemoteDataSource: NoteRemoteDataSource
    ): NoteRepository {
        return DefaultNoteRepository(
            noteLocalDataSource,
            noteRemoteDataSource
        )
    }


}