package com.myproject.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myproject.notes.model.NotesModel

@Dao
interface NotesDao {
    
    @Insert
    suspend fun addData(notesModel: NotesModel)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("UPDATE notes SET title = :title, note = :note, date = :date WHERE id = :id")
    suspend fun update(id: Long, title: String, note: String, date: String)

    @Query("SELECT * from notes")
    fun allNotes(): LiveData<MutableList<NotesModel>>

}