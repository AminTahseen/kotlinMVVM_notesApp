package com.example.kotlinmvvm_notesapp.di

import android.app.Application
import androidx.room.Room
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.local.NoteDatabase
import com.example.kotlinmvvm_notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NoteRepository
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.AddNoteUseCase
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.GetNotesUseCase
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.NoteUseCases
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
    fun provideNoteDatabase(app:Application):NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(db:NoteDatabase):NoteRepository{
        return NoteRepositoryImpl(db.notesDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(repository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository)
        )
    }
}