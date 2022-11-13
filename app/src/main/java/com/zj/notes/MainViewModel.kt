package com.zj.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zj.model.NoteInfo
import com.zj.model.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NotesDatabase.getDatabase(application).noteInfo()

    /**
     * 当前Note列表
     */
    val notesList = noteDao.getNoteInfoList()

    /**
     * 新增便签
     */
    fun insertNoteInfo(noteInfo: NoteInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insert(noteInfo)
        }
    }

}