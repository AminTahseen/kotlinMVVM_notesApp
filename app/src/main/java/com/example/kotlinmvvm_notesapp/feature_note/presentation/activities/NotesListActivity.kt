package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListActivity : AppCompatActivity() {
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        linkXML()
        initUI()
        setObservers()
    }

    private fun linkXML(){}

    private fun initUI(){
        notesViewModel= ViewModelProvider(this@NotesListActivity)
            .get(NotesViewModel::class.java)

        notesViewModel.launchNotes()
    }
    private fun setObservers(){
        notesViewModel.allItem.observe(this) {
            Log.d("NoteListActivity", "${it.title} : ${it.id}")
        }
    }
}