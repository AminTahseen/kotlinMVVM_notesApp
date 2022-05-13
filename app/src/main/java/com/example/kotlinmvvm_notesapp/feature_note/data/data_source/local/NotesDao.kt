package com.example.kotlinmvvm_notesapp.feature_note.data.data_source.local

import androidx.room.*
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM note")
    fun getNotes():Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Query("DELETE FROM note")
    suspend fun deleteAllNotes()

}