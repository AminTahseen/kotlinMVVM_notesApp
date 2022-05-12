package com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels

import androidx.lifecycle.*
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.InvalidNoteException
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val noteUseCases: NoteUseCases):ViewModel() {

    private var _notes= MutableStateFlow(Note())
    private val notes:MutableStateFlow<Note> get() = _notes

    val allItem : LiveData<Note> = notes.asLiveData()

    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun addNoteToLocal(note:Note){
        viewModelScope.launch{
            try {
                noteUseCases.addNoteUseCase(note)
                message.value="Note Successfully Added"
            }
            catch (e: InvalidNoteException){
                message.value=e.message
            }
        }
    }

    fun launchNotes() {
        viewModelScope.launch {
            noteUseCases.getNotesUseCase().collect {
                it.iterator().forEach { note ->
                    _notes.value = note
                }
            }
        }
    }


}