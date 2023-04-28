package com.example.task_sqb.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task_sqb.data.local.room.dao.ContactDao
import com.example.task_sqb.data.local.room.entity.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}