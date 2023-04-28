package com.example.task_sqb.data.repository

import com.example.task_sqb.data.local.room.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getAllContacts(): Flow<List<ContactEntity>>
    fun updateContact(contactEntity: ContactEntity):Flow<String>
    fun addContact(contactEntity: ContactEntity):Flow<String>
    fun deleteContact(contactEntity: ContactEntity):Flow<String>
    fun syncData():Flow<String>
    fun getContactById(id: Int): Flow<ContactEntity?>
}