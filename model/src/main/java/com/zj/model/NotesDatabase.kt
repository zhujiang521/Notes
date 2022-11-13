package com.zj.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * 版权：Zhujiang 个人版权
 *
 * @author zhujiang
 * 创建日期：2022/11/13
 * 描述：PlayNotes
 *
 */
@Database(
    entities = [NoteInfo::class],
    version = 1
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteInfo(): NoteInfoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}