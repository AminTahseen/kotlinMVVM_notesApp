package com.example.kotlinmvvm_notesapp.feature_note.domain.repository
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

// Note repository interface, allows us to define both local and remote data source methods
interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun addNote(note: Note)

    suspend fun deleteAllNotes()
}