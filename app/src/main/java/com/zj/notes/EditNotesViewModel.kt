package com.zj.notes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zj.model.NoteInfo
import com.zj.model.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNotesViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "EditNotesViewModel"
    }

    private val noteDao = NotesDatabase.getDatabase(application).noteInfo()

    val noteInfoLiveData = MutableLiveData<NoteInfo?>()

    val noteState: LiveData<NotesState> get() = _noteState

    private val _noteState = MutableLiveData<NotesState>()

    fun setNotesState(notesState: NotesState) {
        if (notesState == _noteState.value) {
            return
        }
        _noteState.postValue(notesState)
    }

    /**
     * 修改
     */
    fun updateNoteInfo(noteInfo: NoteInfo?) {
        if (noteInfo == null) {
            Log.w(TAG, "updateNoteInfo: noteInfo is null")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = noteDao.update(noteInfo)
            Log.d(TAG, "updateNoteInfo: $update")
        }
    }

    /**
     * 根据id获取便签信息
     */
    fun getNoteInfo(id: Int) {
        if (id < 0) {
            Log.e(TAG, "getNoteInfo: id is null")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val noteInfo = noteDao.getNoteInfoForUid(id)
            Log.e(TAG, "noteInfo: $noteInfo")
            withContext(Dispatchers.Main) {
                noteInfoLiveData.postValue(noteInfo)
            }

        }
    }

    /**
     * 新增便签
     */
    fun insertNoteInfo(noteInfo: NoteInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insert(noteInfo)
        }
    }

}

sealed class NotesState

// 编辑状态
object EditNotesState : NotesState()

// 普通状态
object NormalNotesState : NotesState()