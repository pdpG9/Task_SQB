package com.example.task_sqb.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.task_sqb.data.local.room.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = REPLACE)
    suspend fun addContact(contactEntity: ContactEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addAllContact(contacts: List<ContactEntity>)

    @Delete
    suspend fun deleteContact(contactEntity: ContactEntity)

    @Query("select * from contacts")
    fun getAllContacts(): Flow<List<ContactEntity>>

    @Query("select * from contacts where id= :contactId limit 1")
    suspend fun getContactById(contactId: Int): ContactEntity?

    @Query("select * from contacts where state!=0")
    suspend fun getUnSyncContacts():List<ContactEntity>

    @Query("DELETE FROM contacts")
    suspend fun clearContacts()
}