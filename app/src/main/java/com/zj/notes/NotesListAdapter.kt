package com.zj.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.RecyclerView
import com.zj.model.NoteInfo
import com.zj.notes.databinding.AdapterNotesListBinding

class NotesListAdapter(
    private val notesItemList: List<NoteInfo>,
) : RecyclerView.Adapter<NotesListAdapter.RecyclerHolder>() {

    private var listener: NotesItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerHolder {
        val binding =
            AdapterNotesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val noteInfo = notesItemList[position]
        holder.adapterNotesItem.setContent {
            NotesListItem(noteInfo)
        }
    }

    @Composable
    private fun NotesListItem(noteInfo: NoteInfo) {
        Card(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .clickable {
                    listener?.onItemClick(noteInfo.uid)
                },
            border = BorderStroke(0.5.dp, Color.Gray)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = noteInfo.title,
                maxLines = 1,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                text = noteInfo.data,
                maxLines = 1,
                fontSize = 15.sp
            )
        }
    }

    override fun getItemCount(): Int {
        return notesItemList.size
    }

    fun setOnClickItemListener(listener: NotesItemClickListener) {
        this.listener = listener
    }

    interface NotesItemClickListener {
        fun onItemClick(id: Int)
    }

    class RecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val adapterNotesItem: ComposeView = view.findViewById(R.id.adapter_notes_item)
    }

}