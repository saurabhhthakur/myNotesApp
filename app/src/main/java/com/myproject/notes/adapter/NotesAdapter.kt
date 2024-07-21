package com.myproject.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.myproject.notes.R
import com.myproject.notes.databinding.RowBinding
import com.myproject.notes.model.NotesModel

class NotesAdapter(private val data: MutableList<NotesModel>,private val listener: ManageClicks) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row,
            parent,
            false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.binding.apply {
            title.text = data[position].title.ifEmpty { data[position].note }
            date.text = data[position].date

            row.setOnClickListener {
                listener.click(
                    NotesModel(
                        data[position].id,
                        data[position].title,
                        data[position].note,
                        data[position].date
                    )
                )
            }

            row.setOnLongClickListener {
                listener.longClick(data[position].id)
                true
            }

        }
    }

}