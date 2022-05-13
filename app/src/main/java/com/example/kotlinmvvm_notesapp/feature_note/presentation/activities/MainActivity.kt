package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var addButton:FloatingActionButton
    private lateinit var notesRecycler:RecyclerView
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        linkXML()
        setObservers()
        setNoteRecycler()

        addButton.setOnClickListener {
            val intent = Intent(MainActivity@this,
                AddNoteActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initUI(){
        notesViewModel= ViewModelProvider(this@MainActivity)
            .get(NotesViewModel::class.java)
    }
    private fun setObservers(){
        notesViewModel.launchNotes()
        notesViewModel.allItem.observe(this){
            Log.d("newNote","${it.toString()} \n")

        }
    }
    private fun linkXML(){
        addButton=findViewById(R.id.btn_add_note)
        notesRecycler=findViewById(R.id.notes_list)
    }
    private fun setNoteRecycler(){
        notesRecycler.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            true)
    }


}