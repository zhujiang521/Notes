package com.zj.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 版权：Zhujiang 个人版权
 *
 * @author zhujiang
 * 创建日期：2022/11/13
 * 描述：PlayNotes
 *
 */
@Entity(tableName = "note_info")
data class NoteInfo(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "create_time") val createTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "modified_time") val modifiedTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "title") val title: String = "Default",
    @ColumnInfo(name = "data") val data: String = "Default Data"
)