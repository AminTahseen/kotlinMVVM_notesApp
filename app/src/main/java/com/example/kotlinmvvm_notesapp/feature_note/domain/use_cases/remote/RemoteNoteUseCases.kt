package com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.remote

data class RemoteNoteUseCases(
    val getRemoteNoteUseCases: GetNotesFromRemoteUseCase,
    val postNoteToRemoteUseCase: PostNoteToRemoteUseCase
)
