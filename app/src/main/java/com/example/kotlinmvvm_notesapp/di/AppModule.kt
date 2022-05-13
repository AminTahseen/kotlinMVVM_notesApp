package com.example.kotlinmvvm_notesapp.di

import android.app.Application
import androidx.room.Room
import com.example.kotlinmvvm_notesapp.common.Constants
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.local.NoteDatabase
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.api.NotesApi
import com.example.kotlinmvvm_notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.kotlinmvvm_notesapp.feature_note.data.repository.NotesApiRepositoryImpl
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NoteRepository
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NotesApiRepository
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.local.AddNoteUseCase
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.local.DeleteNoteUseCase
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.local.GetNotesUseCase
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.local.NoteUseCases
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.remote.GetNotesFromRemoteUseCase
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.remote.PostNoteToRemoteUseCase
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.remote.RemoteNoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideNotesUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            deleteAllNotes = DeleteNoteUseCase(repository)
        )
    }
    @Provides
    @Singleton
    fun provideCartRetrofitInstance():NotesApi=
        Retrofit.Builder()
            .baseUrl(Constants.MAIN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotesApi::class.java)

    @Provides
    @Singleton
    fun provideNotesRemoteRepository(api:NotesApi):NotesApiRepository{
        return NotesApiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideNoteRemotesUseCases(notesApiRepository: NotesApiRepository):
            RemoteNoteUseCases {
        return RemoteNoteUseCases(
          getRemoteNoteUseCases = GetNotesFromRemoteUseCase(notesApiRepository),
          postNoteToRemoteUseCase = PostNoteToRemoteUseCase(notesApiRepository)
        )
    }


}