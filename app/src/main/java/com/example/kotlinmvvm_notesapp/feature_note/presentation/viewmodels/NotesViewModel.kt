package com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels

import androidx.lifecycle.*
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotePost
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotesResponseItem
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.InvalidNoteException
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.local.NoteUseCases
import com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.remote.RemoteNoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val notesFromRemoteUseCase: RemoteNoteUseCases
):ViewModel() {

    private var _notes= MutableStateFlow(Note())
    private val notes:MutableStateFlow<Note> get() = _notes

    private var _notesRemote= MutableStateFlow(NotesResponseItem())
    private val notesRemote:MutableStateFlow<NotesResponseItem> get() = _notesRemote

    val allItem : LiveData<Note> = notes.asLiveData()
    val allRemoteItem : LiveData<NotesResponseItem> = notesRemote.asLiveData()


    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val loading:MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
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
    fun addNoteToRemote(note: NotePost){
        viewModelScope.launch {
            try {
                notesFromRemoteUseCase.postNoteToRemoteUseCase(note)
                message.value="Note Successfully Added"
            }
            catch (e: InvalidNoteException){
                message.value=e.message
            }
        }
    }
    fun getNotesFromRemote() {
        try{
            loading.value = false
            viewModelScope.launch {
                notesFromRemoteUseCase.getRemoteNoteUseCases().collect {
                    it.iterator().forEach { notesResponseItem ->
                        _notesRemote.value = notesResponseItem
                    }
                }
                loading.value = true
            }
        }catch (e:Exception){
            loading.value=true
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
    fun deleteAllNotesFromLocal(){
        viewModelScope.launch {
            noteUseCases.deleteAllNotes()
        }
    }
    fun storeAllLocalDataToRemote(localNote:List<NotePost>){
            viewModelScope.launch {
                localNote.iterator().forEach {
                    try {
                        loading.value=true
                        notesFromRemoteUseCase.postNoteToRemoteUseCase(it)
                    } catch (e: InvalidNoteException) {
                        message.value = e.message
                        loading.value=false
                    }
                }
                loading.value=false
            }
    }
}