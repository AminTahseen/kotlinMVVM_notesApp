package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.presentation.adapters.NotesAdapter
import com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var addButton:FloatingActionButton
    private lateinit var syncButton:FloatingActionButton

    private lateinit var notesRecycler:RecyclerView
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesList:ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // load notes from server
        // if internet available
        // { store notes to server }
        // else if internet not available
        // { store to local db }

        initUI()
        linkXML()
        setObservers()
        setNoteRecycler()
        setAdapter()
        addButton.setOnClickListener {
            val intent = Intent(
                this,
                AddNoteActivity::class.java)
            startActivity(intent)
        }
        syncButton.setOnClickListener {
            // if local db is not empty than store local db data
            // to server
        }

    }

    private fun initUI(){
        notesViewModel= ViewModelProvider(this@MainActivity)
            .get(NotesViewModel::class.java)
        notesList=ArrayList()
    }
    private fun setObservers(){
        notesViewModel.launchNotes()
        notesViewModel.allItem.observe(this){
            if(it.id!=null) {
                notesList.add(it)

            }
            setAdapter()
        }
    }
    private fun linkXML(){
        addButton=findViewById(R.id.btn_add_note)
        syncButton=findViewById(R.id.btn_sync_notes)
        notesRecycler=findViewById(R.id.notes_list)
    }
    private fun setNoteRecycler(){
        notesRecycler.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setAdapter(){
        val noteAdapter=NotesAdapter(notesList,this@MainActivity)
        notesRecycler.adapter=noteAdapter
    }


}