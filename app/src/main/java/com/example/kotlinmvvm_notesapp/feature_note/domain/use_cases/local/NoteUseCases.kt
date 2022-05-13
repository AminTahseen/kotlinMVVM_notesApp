package com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.local

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val deleteAllNotes:DeleteNoteUseCase
)
