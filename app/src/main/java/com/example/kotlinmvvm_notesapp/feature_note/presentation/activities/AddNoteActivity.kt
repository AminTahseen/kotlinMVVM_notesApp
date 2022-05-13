package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.common.ConnectionLiveData
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotePost
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.toNote
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.toNotePost
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.toNoteResponseItem
import com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import com.yahiaangelo.markdownedittext.MarkdownEditText
import com.yahiaangelo.markdownedittext.MarkdownStylesBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity() {
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var addButton: Button
    private lateinit var noteTitleEditText: EditText
    private lateinit var noteDescEditText: MarkdownEditText
    private lateinit var notesDescEdittextStylesBar: MarkdownStylesBar
    private lateinit var connectionLiveData : ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        linkXML()
        initUI()

        addButton.setOnClickListener {
            validate(it)
        }

    }
    private fun validate(view: View){
        val noteTitle=noteTitleEditText.text.toString()
        val noteDesc=noteDescEditText.getMD()
        val currentTimestamp = System.currentTimeMillis()
        var obj= NotePost(
            note_id = Random().nextInt(),
            note_title = noteTitle,
            note_content = noteDesc,
            note_timestamp = currentTimestamp.toInt()
        )
        connectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                notesViewModel.addNoteToRemote(obj)
            }else{
                notesViewModel.addNoteToLocal(obj.toNote())
            }
        }
        setObservers(view)
    }
    private fun linkXML(){
        addButton=findViewById(R.id.addNote)
        noteTitleEditText=findViewById(R.id.noteTitle)
        noteDescEditText=findViewById(R.id.notesDecMarkdown)
        notesDescEdittextStylesBar=findViewById(R.id.stylesbar)
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
        connectionLiveData=ConnectionLiveData(application)

    }
}