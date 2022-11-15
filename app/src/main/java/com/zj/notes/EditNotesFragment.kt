package com.zj.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zj.model.NoteInfo
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
                    val notesState by viewModel.noteState.observeAsState(NormalNotesState)
                    var titleValue by remember { mutableStateOf("") }
                    var contentValue by remember { mutableStateOf("") }
                    if (notesState is NormalNotesState) {
                        titleValue = noteInfo?.title ?: ""
                        contentValue = noteInfo?.data ?: ""
                    }
                    Log.d(
                        "ZHUJIANG123",
                        "onCreateView: noteInfo:$noteInfo titleValue:$titleValue   contentValue:$contentValue"
                    )
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { /*.标题栏..*/
                            TopAppBar(
                                title = { Text(if (notesState is EditNotesState) "编辑便签" else "便签") },
                                navigationIcon = {
                                    IconButton(onClick = { activity?.onBackPressed() }) {
                                        Icon(Icons.Filled.ArrowBack, "")
                                    }
                                },
                                actions = {
                                    if (notesState is EditNotesState) {
                                        IconButton(onClick = {
                                            if (noteInfo == null) {
                                                viewModel.insertNoteInfo(
                                                    NoteInfo(
                                                        title = titleValue,
                                                        data = contentValue
                                                    )
                                                )
                                            } else {
                                                viewModel.updateNoteInfo(noteInfo?.apply {
                                                    title = titleValue
                                                    data = contentValue
                                                })
                                            }
                                            activity?.onBackPressed()
                                        }) {
                                            Icon(Icons.Filled.Done, "")
                                        }
                                    } else {
                                        IconButton(onClick = { /*.点击事件..*/ }) {
                                            Icon(Icons.Filled.Share, "")
                                        }
                                    }
                                })
                        },
                        floatingActionButton = { /*.悬浮按钮..*/
                            FloatingActionButton(onClick = {
                                // Floating点击事件
                            }) {
                                Text("OK")
                            }
                        },
                        bottomBar = {
                            if (notesState is EditNotesState) {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    IconButton(modifier = Modifier.weight(1f), onClick = { }) {
                                        Icon(Icons.Filled.ArrowBack, "")
                                    }
                                    IconButton(modifier = Modifier.weight(1f), onClick = { }) {
                                        Icon(Icons.Filled.ArrowForward, "")
                                    }
                                    IconButton(modifier = Modifier.weight(1f), onClick = { }) {
                                        Icon(Icons.Filled.ShoppingCart, "")
                                    }
                                    IconButton(modifier = Modifier.weight(1f), onClick = { }) {
                                        Icon(Icons.Filled.ThumbUp, "")
                                    }
                                    IconButton(modifier = Modifier.weight(1f), onClick = { }) {
                                        Icon(Icons.Filled.AccountBox, "")
                                    }
                                }
                            }
                        },
                        content = { /*.主内容..*/
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it)
                                    .onFocusChanged { focusState ->
                                        if (focusState.hasFocus) {
                                            viewModel.setNotesState(EditNotesState)
                                        } else {
                                            viewModel.setNotesState(NormalNotesState)
                                        }
                                    }
                            ) {
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
                    )
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