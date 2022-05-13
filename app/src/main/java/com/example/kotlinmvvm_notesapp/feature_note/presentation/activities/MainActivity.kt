package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.common.ConnectionLiveData
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.toNote
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.NoteDisplay
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.toNoteDisplay
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
    private lateinit var notesList:ArrayList<NoteDisplay>

    private lateinit var connectionLiveData : ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // load notes from server
        // add Note :
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
            // to server, clear local db

            connectionLiveData.observe(this) { isConnected ->
                if (isConnected) {
                    notesViewModel.deleteAllNotesFromLocal()
                    notesViewModel.getNotesFromRemote()
                    notesViewModel.launchNotes()
                    Log.d("InternetCheck","Available")
                } else {
                    notesViewModel.launchNotes()
                    Log.d("InternetCheck","Not Available")
                }
            }
        }
    }

    private fun initUI(){
        notesViewModel= ViewModelProvider(this@MainActivity)
            .get(NotesViewModel::class.java)
        notesList=ArrayList()
        connectionLiveData=ConnectionLiveData(application)
    }
    private fun setObservers(){
        if(notesList.size>0){
            notesList.clear()
        }
        notesViewModel.launchNotes()
        notesViewModel.allItem.observe(this){
            if(it.id!=null) {
                if(!notesList.contains(it.toNoteDisplay())) {
                    notesList.add(it.toNoteDisplay())
                }
            }
            Log.d("LocalNotes","${it.id}")
            setAdapter()
        }
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Loading")
        notesViewModel.loading.observe(this){
            if(it==true){
                progressDialog.dismiss()
            }else{
                progressDialog.show()
            }
        }

        notesViewModel.allRemoteItem.observe(this){
            Log.d("RemoteNotes",it.note_title)
          //  notesViewModel.addNoteToLocal()
            notesViewModel.addNoteToLocal(it.toNote())
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