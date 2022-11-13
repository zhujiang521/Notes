package com.zj.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zj.model.NoteInfo
import com.zj.notes.databinding.AdapterNotesListBinding

class NotesListAdapter(
    private val notesItemList: List<NoteInfo>,
) : BaseRecyclerAdapter<AdapterNotesListBinding>() {

    private var listener: NotesItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerHolder<AdapterNotesListBinding> {
        val binding =
            AdapterNotesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseRecyclerHolder(binding)
    }

    override fun onBaseBindViewHolder(position: Int, binding: AdapterNotesListBinding) {
        val data = notesItemList[position]
        binding.apply {
            adapterNitesItem.setOnClickListener {
                listener?.onItemClick(data.uid)
            }
            adapterNotesName.text = data.title
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

}