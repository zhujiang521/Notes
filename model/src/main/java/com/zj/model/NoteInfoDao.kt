package com.zj.model

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * 版权：Zhujiang 个人版权
 *
 * @author zhujiang
 * 创建日期：2022/11/13
 * 描述：PlayNotes
 *
 */
@Dao
interface NoteInfoDao {

    @Query("SELECT * FROM note_info order by uid desc")
    fun getNoteInfoList(): LiveData<List<NoteInfo>>

    @Query("SELECT * FROM note_info where uid = :id")
    suspend fun getNoteInfoForUid(id: Int): NoteInfo?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(noteInfoList: List<NoteInfo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noteInfo: NoteInfo)

    @Update
    suspend fun update(noteInfo: NoteInfo): Int

    @Delete
    suspend fun delete(noteInfo: NoteInfo): Int

    @Delete
    suspend fun deleteList(noteInfoList: List<NoteInfo>): Int

    @Query("DELETE FROM note_info")
    suspend fun deleteAll()
}
