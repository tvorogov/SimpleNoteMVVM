package com.tvorogov.simplenotemvvm.features.editnote

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tvorogov.simplenotemvvm.R
import com.tvorogov.simplenotemvvm.databinding.FragmentEditNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private val viewModel: EditNoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditNoteBinding.bind(view)

        binding.apply {
            editTextNoteTitle.setText(viewModel.noteTitle)
            editTextNoteContent.setText(viewModel.noteContent)
            textViewDateEdited.isVisible = viewModel.note != null
            textViewDateEdited.text = "Edited: ${viewModel.note?.lastEditedDateFormatted}"

            fabSaveNote.setOnClickListener {
                viewModel.onSaveClicked()
            }

            editTextNoteTitle.addTextChangedListener {
                viewModel.noteTitle = it.toString()
            }

            editTextNoteContent.addTextChangedListener {
                viewModel.noteContent = it.toString()
            }

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.editNoteEvent.collect { event ->
                when (event) {
                    is EditNoteViewModel.EditNoteEvent.NavigateBackWithResult -> {
                        binding.editTextNoteTitle.clearFocus()
                        binding.editTextNoteContent.clearFocus()
                        setFragmentResult(
                            "edit_request",
                            bundleOf("edit_note_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }
            }
        }

    }

}