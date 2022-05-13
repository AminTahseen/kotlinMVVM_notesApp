package com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.local

import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val noteRepository: NoteRepository){

    operator fun invoke():Flow<List<Note>>{
        return noteRepository.getNotes()
    }
}