package com.example.kotlinmvvm_notesapp.feature_note.data.repository

import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.local.NotesDao
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

// Note repository implements, allows us to implements both local and remote data source methods from interface
class NoteRepositoryImpl(private val notesDao: NotesDao):NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return notesDao.getNotes()
    }

    override suspend fun addNote(note: Note) {
        notesDao.addNote(note)
    }
}