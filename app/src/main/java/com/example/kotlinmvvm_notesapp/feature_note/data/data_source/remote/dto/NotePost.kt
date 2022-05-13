package com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto

import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note

data class NotePost(
    val note_id: Int?=null,
    val note_content: String="",
    val note_timestamp: Int=-1,
    val note_title: String=""
)

fun NotePost.toNote(): Note {
    return Note(
        title = note_title,
        description = note_content,
        timestamp = note_timestamp.toLong(),
    )
}
