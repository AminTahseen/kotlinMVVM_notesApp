package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.common.networkHandler.ConnectivityManager
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.toNote
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.NoteDisplay
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.toNoteDisplay
import com.example.kotlinmvvm_notesapp.feature_note.presentation.adapters.NotesAdapter
import com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var addButton:FloatingActionButton
    private lateinit var syncButton:FloatingActionButton

    private lateinit var notesRecycler:RecyclerView
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesList:ArrayList<NoteDisplay>

    private lateinit var statusStrip:TextView


    @Inject
    lateinit var connectivityManager: ConnectivityManager

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
            connectivityManager.checkNetwork.observe(this@MainActivity){ isConnected->
                if (isConnected) {
                    statusStrip.visibility= View.GONE

                    //    notesViewModel.storeAllLocalDataToRemote()
                    notesViewModel.deleteAllNotesFromLocal()
                    notesViewModel.getNotesFromRemote()
                    notesViewModel.launchNotes()
                    Log.d("InternetCheck", "Available")
                } else {
                    statusStrip.visibility= View.VISIBLE
                    notesViewModel.launchNotes()
                    Log.d("InternetCheck", "Not Available")
                }
            }

        }

    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }
    private fun initUI(){
        notesViewModel= ViewModelProvider(this@MainActivity)
            .get(NotesViewModel::class.java)
        notesList=ArrayList()
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
            notesViewModel.addNoteToLocal(it.toNote())
        }
    }
    private fun linkXML(){
        addButton=findViewById(R.id.btn_add_note)
        syncButton=findViewById(R.id.btn_sync_notes)
        notesRecycler=findViewById(R.id.notes_list)
        statusStrip=findViewById(R.id.status)
    }
    private fun setNoteRecycler(){
        notesRecycler.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setAdapter(){
        val noteAdapter=NotesAdapter(notesList,this@MainActivity)
        notesRecycler.adapter=noteAdapter
    }

}