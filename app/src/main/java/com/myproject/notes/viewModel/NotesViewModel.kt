package com.myproject.notes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.notes.model.NotesModel
import com.myproject.notes.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: Repository): ViewModel() {

    val data : LiveData<MutableList<NotesModel>> = repository.allNotes()

    fun addData(notesModel: NotesModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(notesModel)
        }
    }

    fun updateData(id: Long, title: String, note: String, date: String){
     viewModelScope.launch(Dispatchers.IO) {
         repository.update(id, title, note, date)
     }
    }

    fun deleteData(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(id)
        }
    }


}