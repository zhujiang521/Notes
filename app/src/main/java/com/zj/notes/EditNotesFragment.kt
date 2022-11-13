package com.zj.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zj.notes.ui.theme.NotesTheme

private const val NOTES_ITEM_ID = "NOTES_ITEM_ID"

class EditNotesFragment : Fragment() {

    private var notesItemId: Int = -1
    private val viewModel by viewModels<EditNotesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            notesItemId = it.getInt(NOTES_ITEM_ID)
            viewModel.getNoteInfo(notesItemId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NotesTheme {
                    val noteInfo by viewModel.noteInfoLiveData.observeAsState()
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(40.dp))
                        val title = noteInfo?.title ?: "没有"
                        Text(text = title)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            EditNotesFragment().apply {
                arguments = Bundle().apply {
                    putInt(NOTES_ITEM_ID, id)
                }
            }
    }

}