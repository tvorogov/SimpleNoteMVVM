package com.tvorogov.simplenotemvvm.features.notes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tvorogov.simplenotemvvm.R
import com.tvorogov.simplenotemvvm.data.Note
import com.tvorogov.simplenotemvvm.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes), NotesAdapter.OnItemClickListener{

    private val viewModel: NotesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNotesBinding.bind(view)

        val notesAdapter = NotesAdapter(this)


        binding.apply {
            recyclerViewNotes.apply {
                adapter = notesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            fabAddNote.setOnClickListener {
                viewModel.onAddNewNotesClicked()
            }



        }

        setFragmentResultListener("edit_request") {_, bundle ->
            val result = bundle.getInt("edit_note_result")
            viewModel.onEditResult(result)
        }

        viewModel.notes.observe(viewLifecycleOwner) {
            notesAdapter.submitList(it)
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notesEvent.collect { event ->
                when (event) {
                    is NotesViewModel.NotesEvent.NavigateToAddNoteScreen -> {
                        val action = NotesFragmentDirections.actionNotesFragmentToEditNoteFragment()
                        findNavController().navigate(action)
                    }
                    is NotesViewModel.NotesEvent.NavigateToEditNoteScreen -> {
                        val action =
                            NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(event.note)
                        findNavController().navigate(action)
                    }
                    is NotesViewModel.NotesEvent.ShowEditResultMessage -> {
                        Snackbar.make(requireView(),event.msg,Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    override fun onItemClick(note: Note) {
        viewModel.onNoteClicked(note)
    }
}