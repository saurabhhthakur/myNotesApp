package com.myproject.notes.repository

import androidx.lifecycle.LiveData
import com.myproject.notes.database.NotesDao
import com.myproject.notes.model.NotesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository(private val notesDao: NotesDao) {

    suspend fun add(notesModel: NotesModel) {
        withContext(Dispatchers.IO) {
            notesDao.addData(notesModel)
        }
    }

    suspend fun update(id: Long, title: String, note: String, date: String) {
        withContext(Dispatchers.IO) {
            notesDao.update(id, title, note, date)
        }
    }

    suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            notesDao.delete(id)
        }
    }

    fun allNotes(): LiveData<MutableList<NotesModel>> {
        return notesDao.allNotes()
    }

}