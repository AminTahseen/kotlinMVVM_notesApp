package com.example.kotlinmvvm_notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception

@Entity
data class NoteDisplay(
    val title:String="",
    val description:String="",
    val timestamp:Long=-1,
    val id:Int?=null
)

