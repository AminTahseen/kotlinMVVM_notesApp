package com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.remote

import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotePost
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotesResponseItem
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.InvalidNoteException
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NotesApiRepository

class PostNoteToRemoteUseCase
    (private val notesApiRepository: NotesApiRepository) {
    suspend operator fun invoke(note:NotePost){
        if(note.note_title.isBlank()){
            throw InvalidNoteException("Note title cannot be blank !")
        }
        if(note.note_content.isBlank()){
            throw InvalidNoteException("Note description cannot be blank !")
        }
        notesApiRepository.postNoteToRemote(note)
    }
}