package com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases

import com.example.kotlinmvvm_notesapp.feature_note.domain.model.InvalidNoteException
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(private val noteRepository: NoteRepository) {

   suspend operator fun invoke(note:Note){
       if(note.title.isBlank()){
           throw InvalidNoteException("Note title cannot be blank !")
       }
       if(note.description.isBlank()){
           throw InvalidNoteException("Note description cannot be blank !")
       }
        noteRepository.addNote(note)
    }
}