package com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.local

import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(){
        return noteRepository.deleteAllNotes()
    }
}