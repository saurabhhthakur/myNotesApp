package com.myproject.notes.adapter

import com.myproject.notes.model.NotesModel

interface ManageClicks {
    fun click(notesModel: NotesModel)
    fun longClick(id:Long)
}