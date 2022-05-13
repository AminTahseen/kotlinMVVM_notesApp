package com.example.kotlinmvvm_notesapp.feature_note.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_notesapp.R
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.Note
import com.example.kotlinmvvm_notesapp.feature_note.domain.model.NoteDisplay

class NotesAdapter(private val notesList: List<NoteDisplay>,private val context:Context):
RecyclerView.Adapter<NotesAdapter.NotesAdapterViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NotesAdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotesAdapterViewHolder, position: Int) {
        val noteItem=notesList[position]
        holder.noteTitle.text=noteItem.title
        holder.noteDesc.text=noteItem.description
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
    class NotesAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val noteTitle:TextView=itemView.findViewById(R.id.note_title)
        val noteDesc:TextView=itemView.findViewById(R.id.note_content)

    }
}