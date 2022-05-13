package com.example.kotlinmvvm_notesapp.feature_note.data.repository

import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.api.NotesApi
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotePost
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotesResponseItem
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NotesApiRepository
import retrofit2.Call
import javax.inject.Inject

class NotesApiRepositoryImpl @Inject constructor(private val noteApi:NotesApi):NotesApiRepository {
    
    override suspend fun getNotesFromRemote(): List<NotesResponseItem> {
        return noteApi.getNotesFromRemote()
    }

    override suspend fun postNoteToRemote(note: NotePost){
        return noteApi.postNoteToRemote(note)
    }

}