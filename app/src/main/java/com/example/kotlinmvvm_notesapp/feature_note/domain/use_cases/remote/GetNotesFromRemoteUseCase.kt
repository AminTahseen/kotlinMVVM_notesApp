package com.example.kotlinmvvm_notesapp.feature_note.domain.use_cases.remote

import android.util.Log
import com.example.kotlinmvvm_notesapp.feature_note.data.data_source.remote.dto.NotesResponseItem
import com.example.kotlinmvvm_notesapp.feature_note.domain.repository.NotesApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetNotesFromRemoteUseCase(private val notesApiRepository: NotesApiRepository) {
    suspend operator fun invoke():Flow<List<NotesResponseItem>> = flow {
        try {
            val notes = notesApiRepository.getNotesFromRemote().toList()
            emit(value = notes)
        }catch (e:HttpException){
            Log.d("exceptionHTTP",e.message())
            emit(emptyList())
        }
     }
}