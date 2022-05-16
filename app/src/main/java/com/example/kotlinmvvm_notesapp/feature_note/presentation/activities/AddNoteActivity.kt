package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.common.networkHandler.ConnectivityManager
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotePost
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.toNote
import com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import com.yahiaangelo.markdownedittext.MarkdownEditText
import com.yahiaangelo.markdownedittext.MarkdownStylesBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity() {
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var addButton: Button
    private lateinit var noteTitleEditText: EditText
    private lateinit var noteDescEditText: MarkdownEditText
    private lateinit var notesDescEdittextStylesBar: MarkdownStylesBar
    private lateinit var statusStrip: TextView

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        linkXML()
        initUI()
        addButton.setOnClickListener {
            connectivityManager.checkNetwork.observe(this@AddNoteActivity){ isConnected->
                if (isConnected) {
                    Log.d("InsideCheck","internet on")
                    validate(it,isConnected)
                    statusStrip.visibility=View.INVISIBLE
                }else{
                    statusStrip.visibility=View.VISIBLE
                    Log.d("InsideCheck","internet off")
                    validate(it,isConnected)
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
    private fun validate(view: View,isConnected:Boolean){
        val noteTitle=noteTitleEditText.text.toString()
        val noteDesc=noteDescEditText.getMD()
        val currentTimestamp = System.currentTimeMillis()
        var obj= NotePost(
            note_id = Random().nextInt(),
            note_title = noteTitle,
            note_content = noteDesc,
            note_timestamp = currentTimestamp.toInt()
        )
        setObservers(view)
            if (isConnected) {
                notesViewModel.addNoteToRemote(obj)
            }else{
                notesViewModel.addNoteToLocal(obj.toNote())
            }
    }
    private fun linkXML(){
        addButton=findViewById(R.id.addNote)
        noteTitleEditText=findViewById(R.id.noteTitle)
        noteDescEditText=findViewById(R.id.notesDecMarkdown)
        notesDescEdittextStylesBar=findViewById(R.id.stylesbar)
        statusStrip=findViewById(R.id.status)
       notesDescEdittextStylesBar.stylesList =
           arrayOf(MarkdownEditText.TextStyle.BOLD,
               MarkdownEditText.TextStyle.ITALIC,
               MarkdownEditText.TextStyle.LINK,
               MarkdownEditText.TextStyle.ORDERED_LIST,
               MarkdownEditText.TextStyle.UNORDERED_LIST
           )
        noteDescEditText.setStylesBar(notesDescEdittextStylesBar)
    }
    private fun setObservers(view:View) {
        notesViewModel.message.observe(this) {
            val snack = Snackbar.make(
                view,
                notesViewModel.message.value.toString(),
                Snackbar.LENGTH_LONG
            )
            snack.show()
            if(notesViewModel.message.
                value.equals("Note Successfully Added")) {
                Handler().postDelayed({
                    finish()
                }, 2000)
            }
        }
    }
    private fun initUI(){
        notesViewModel= ViewModelProvider(this@AddNoteActivity)
            .get(NotesViewModel::class.java)
    }
}