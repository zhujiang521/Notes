package com.zj.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NotesTheme {
                    val noteInfo by viewModel.noteInfoLiveData.observeAsState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        var titleValue by remember { mutableStateOf("") }
                        var contentValue by remember { mutableStateOf("") }
//                        titleValue = noteInfo?.title ?: ""
//                        contentValue = noteInfo?.data ?: ""
                        Log.d(
                            "ZHUJIANG123",
                            "onCreateView: noteInfo:$noteInfo titleValue:$titleValue   contentValue:$contentValue"
                        )
                        TextField(
                            value = titleValue,
                            onValueChange = { titleValue = it },
                            label = { Text("Notes title") },
                            maxLines = 1,
                            textStyle = TextStyle(
                                color = Color.Blue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            value = contentValue,
                            onValueChange = { contentValue = it },
                            textStyle = TextStyle(fontSize = 15.sp),
                            label = { Text("Notes Content") },
                            modifier = Modifier.fillMaxSize()
                        )

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