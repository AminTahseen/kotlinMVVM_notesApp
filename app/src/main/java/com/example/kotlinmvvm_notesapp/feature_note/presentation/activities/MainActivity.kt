package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private lateinit var addButton:Button
    private lateinit var loadButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linkXML()

        addButton.setOnClickListener {
            val intent = Intent(MainActivity@this,
                AddNoteActivity::class.java)
            startActivity(intent)
        }

        loadButton.setOnClickListener {
            val intent = Intent(MainActivity@this,
                NotesListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun linkXML(){
        addButton=findViewById(R.id.addNoteScreen)
        loadButton=findViewById(R.id.loadNotesScreen)
    }

}