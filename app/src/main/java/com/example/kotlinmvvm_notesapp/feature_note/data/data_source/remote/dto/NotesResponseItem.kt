package com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto

import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.NoteDisplay
import java.lang.Exception

data class NotesResponseItem(
    val __v: Int?=null,
    val _id: String?=null,
    val note_content: String="",
    val note_id: Int=-1,
    val note_timestamp: Int=-1,
    val note_title: String=""
)

fun NotesResponseItem.toNoteDisplay():NoteDisplay{
    return NoteDisplay(
        title = note_title,
        description = note_content,
        timestamp = note_timestamp.toLong(),
    )
}
fun NotesResponseItem.toNote():Note{
    return Note(
        title = note_title,
        description = note_content,
        timestamp = note_timestamp.toLong(),
    )
}
