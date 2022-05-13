package com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.api

import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotePost
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotesResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NotesApi {
    @GET("/notes/ReadAll")
    suspend fun getNotesFromRemote(): List<NotesResponseItem>

    @POST("/notes/create")
    suspend fun postNoteToRemote(@Body note:NotePost)
}