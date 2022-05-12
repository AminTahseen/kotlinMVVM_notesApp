package com.example.kotlinmvvm_notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception

@Entity
data class Note(
    val title:String="",
    val description:String="",
    val timestamp:Long=-1,
    @PrimaryKey val id:Int?=null
)

class InvalidNoteException(message:String):Exception(message)
