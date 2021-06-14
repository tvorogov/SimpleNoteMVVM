package com.tvorogov.simplenotemvvm.features.notes

import androidx.lifecycle.*
import com.tvorogov.simplenotemvvm.ADD_NOTE_RESULT_OK
import com.tvorogov.simplenotemvvm.EDIT_NOTE_RESULT_DELETED
import com.tvorogov.simplenotemvvm.EDIT_NOTE_RESULT_OK
import com.tvorogov.simplenotemvvm.data.Note
import com.tvorogov.simplenotemvvm.data.source.NoteRepository
import com.tvorogov.simplenotemvvm.data.source.local.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {


    val notes = noteRepository.getAllNotes().asLiveData()

    private val notesEventChannel = Channel<NotesEvent>()
    val notesEvent = notesEventChannel.receiveAsFlow()

    fun onAddNewNotesClicked() = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToAddNoteScreen)
    }

    fun onNoteClicked(note: Note) = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToEditNoteScreen(note))
    }

    fun onEditResult(result: Int) {
        when (result) {
            ADD_NOTE_RESULT_OK -> showEditResultMessage("Note added")
            EDIT_NOTE_RESULT_OK -> showEditResultMessage("Note edited")
            EDIT_NOTE_RESULT_DELETED -> showEditResultMessage("Empty note discarded")
        }
    }

    private fun showEditResultMessage(text: String) = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.ShowEditResultMessage(text))
    }

    fun onNoteSwiped(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
        notesEventChannel.send(NotesEvent.ShowUndoDeleteNoteMessage(note))
    }
    fun onUndoDeleteClicked(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    sealed class NotesEvent {
        object NavigateToAddNoteScreen : NotesEvent()
        data class NavigateToEditNoteScreen(val note: Note) : NotesEvent()
        data class ShowEditResultMessage(val msg: String) : NotesEvent()
        data class ShowUndoDeleteNoteMessage(val note: Note) : NotesEvent()

    }
}
