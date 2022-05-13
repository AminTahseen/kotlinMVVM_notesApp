package com.example.kotlinmvvm_notesapp.feature_note.domain.repository

import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotePost
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotesResponseItem
import retrofit2.Call

interface NotesApiRepository {

    suspend fun getNotesFromRemote(): List<NotesResponseItem>

    suspend fun postNoteToRemote(note:NotePost)
}