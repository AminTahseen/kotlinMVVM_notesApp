package com.example.kotlinmvvm_notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotePost
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotesResponseItem
import java.lang.Exception

@Entity
data class Note(
    val title:String="",
    val description:String="",
    val timestamp:Long=-1,
    @PrimaryKey val id:Int?=null
)

class InvalidNoteException(message:String):Exception(message)

fun Note.toNoteDisplay():NoteDisplay{
    return NoteDisplay(
        title = title,
        description = description,
        timestamp = timestamp,
        id=id
    )
}

fun Note.toNoteResponseItem():NotesResponseItem{
    return NotesResponseItem(
        note_title =title,
        note_content = description,
        note_timestamp = timestamp.toInt()
    )
}
fun Note.toNotePost():NotePost{
    return NotePost(
        note_id=id,
        note_title = title,
        note_content = description,
        note_timestamp = timestamp.toInt(),
    )
}