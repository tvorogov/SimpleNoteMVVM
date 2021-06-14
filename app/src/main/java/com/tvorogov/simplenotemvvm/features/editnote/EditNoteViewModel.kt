package com.tvorogov.simplenotemvvm.features.editnote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tvorogov.simplenotemvvm.ADD_NOTE_RESULT_OK
import com.tvorogov.simplenotemvvm.EDIT_NOTE_RESULT_DELETED
import com.tvorogov.simplenotemvvm.EDIT_NOTE_RESULT_OK
import com.tvorogov.simplenotemvvm.data.Note
import com.tvorogov.simplenotemvvm.data.source.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val note = state.get<Note>("note")

    var noteTitle = state.get<String>("noteTitle") ?: note?.title ?: ""
        set(value) {
            field = value
            state.set("noteTitle", value)
        }


    var noteContent = state.get<String>("noteContent") ?: note?.content ?: ""
        set(value) {
            field = value
            state.set("noteContent", value)
        }

    var notePinned = state.get<Boolean>("notePinned") ?: note?.pinned ?: false
        set(value) {
            field = value
            state.set("notePinned", value)
        }

    private val editNoteEventChannel = Channel<EditNoteEvent>()
    val editNoteEvent = editNoteEventChannel.receiveAsFlow()

    fun onSaveClicked() {

        if (noteTitle.isBlank() && noteContent.isBlank()) {
            onEmptyNote()
            return
        }

        if (note != null) {
            val updatedNote = note.copy( title = noteTitle, content = noteContent, lastEdited = System.currentTimeMillis())
            updateNote(updatedNote)
        } else {
            val newNote = Note(title = noteTitle, content = noteContent, lastEdited = System.currentTimeMillis())
            createNote(newNote)
        }

    }

    private fun onEmptyNote() = viewModelScope.launch {
        editNoteEventChannel.send(EditNoteEvent.NavigateBackWithResult(EDIT_NOTE_RESULT_DELETED))
    }

    private fun createNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
        editNoteEventChannel.send(EditNoteEvent.NavigateBackWithResult(ADD_NOTE_RESULT_OK))
    }

    private fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
        editNoteEventChannel.send(EditNoteEvent.NavigateBackWithResult(EDIT_NOTE_RESULT_OK))
    }


    sealed class EditNoteEvent {

        data class NavigateBackWithResult(val result: Int) : EditNoteEvent()
    }
}