package com.tvorogov.simplenotemvvm.features.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tvorogov.simplenotemvvm.data.Note
import com.tvorogov.simplenotemvvm.databinding.ItemNoteBinding

class NotesAdapter : ListAdapter<Note, NotesAdapter.NotesViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NotesViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.bind(currentItem)
    }

    class NotesViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(note: Note) {

            binding.apply {
                textViewNoteTitle.text = note.title
                textViewNoteContent.text = note.content
            }

        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {

            return oldItem == newItem
        }
    }


}