package com.example.kotlinmvvm_notesapp.feature_note.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.presentation.viewmodels.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity() {
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var addButton: Button
    private lateinit var noteTitleEditText: EditText
    private lateinit var noteDescEditText: EditText

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
        val noteDesc=noteDescEditText.text.toString();
        val currentTimestamp = System.currentTimeMillis()
        var obj= Note(
            title = noteTitle,
            description = noteDesc,
            timestamp = currentTimestamp
        )
        notesViewModel.addNoteToLocal(obj)
        setObservers(view)
    }
    private fun linkXML(){
        addButton=findViewById(R.id.addNote)
        noteTitleEditText=findViewById(R.id.noteTitle)
        noteDescEditText=findViewById(R.id.noteDesc)
    }
    private fun setObservers(view:View) {
        notesViewModel.message.observe(this) {
            val snack = Snackbar.make(
                view,
                notesViewModel.message.value.toString(),
                Snackbar.LENGTH_LONG)
            snack.show()
        }
    }
    private fun initUI(){
        notesViewModel= ViewModelProvider(this@AddNoteActivity)
            .get(NotesViewModel::class.java)
    }
}