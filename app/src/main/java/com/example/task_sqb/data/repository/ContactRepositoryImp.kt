package com.example.task_sqb.data.repository

import android.content.Context
import android.util.Log
import com.example.task_sqb.data.local.mapper.mapToAddContactRequest
import com.example.task_sqb.data.local.mapper.mapToEntity
import com.example.task_sqb.data.local.mapper.mapToUpdateRequest
import com.example.task_sqb.data.local.room.dao.ContactDao
import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.local.room.entity.ContactState
import com.example.task_sqb.data.network.api.ContactApi
import com.example.task_sqb.data.network.response.ErrorResponse
import com.example.task_sqb.utils.isConnected
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ContactRepositoryImp @Inject constructor(
    private val contactApi: ContactApi,
    private val contactDao: ContactDao,
    private val gson: Gson,
    @ApplicationContext private val context: Context
) : ContactRepository {
    override fun getAllContacts(): Flow<List<ContactEntity>> = contactDao.getAllContacts()


    override fun updateContact(contactEntity: ContactEntity): Flow<String> = flow {
        if (isConnected(context)) {
            val response = contactApi.updateContact(contactEntity.mapToUpdateRequest())
            if (response.isSuccessful) {
                response.body()?.let { contact ->
                    contactDao.addContact(contact.mapToEntity())
                    emit("Contact online updated!")
                }
            } else emit(gsonConverter(response))
        } else {
            contactEntity.state = ContactState.OFFLINE_CHANGED.value
            contactDao.addContact(contactEntity)
            emit("Contact offline updated!")
        }
    }

    override fun addContact(contactEntity: ContactEntity): Flow<String> = flow {
        if (isConnected(context)) {
            val response = contactApi.addContact(contactEntity.mapToAddContactRequest())
            if (response.isSuccessful) {
                response.body()?.let { contact ->
                    contactDao.addContact(contact.mapToEntity())
                    emit("Contact online added!")
                }
            } else emit(gsonConverter(response))

        } else {
            contactEntity.state = ContactState.OFFLINE_ADDED.value
            contactDao.addContact(contactEntity)
            emit("Contact offline added!")
        }
    }

    override fun deleteContact(contactEntity: ContactEntity): Flow<String> = flow {
        if (isConnected(context)) {
            val response = contactApi.deleteContact(contactEntity.id)
            if (response.isSuccessful) {
                response.body()?.let {
                    contactDao.deleteContact(contactEntity)
                    emit("Contact online deleted!")
                }
            } else emit(gsonConverter(response))
        } else {
            contactEntity.state = ContactState.OFFLINE_DELETED.value
            contactDao.deleteContact(contactEntity)
            emit("Contact offline deleted!")
        }
    }

    override fun syncData(): Flow<String> = flow {
        if (isConnected(context = context)) {
            contactDao.getUnSyncContacts().forEach {
                when (it.state) {
                    ContactState.OFFLINE_CHANGED.value -> {
                        val response = contactApi.updateContact(it.mapToUpdateRequest())
                        if (response.isSuccessful) {
                            response.body()?.let { contact ->
                                contactDao.addContact(contact.mapToEntity())
                                emit("${contact.firstName} successfully changed to server!")
                            }
                        } else emit(gsonConverter(response))
                    }

                    ContactState.OFFLINE_ADDED.value -> {
                        val response = contactApi.addContact(it.mapToAddContactRequest())
                        if (response.isSuccessful) {
                            response.body()?.let { contact ->
                                contactDao.deleteContact(it)
                                contactDao.addContact(contact.mapToEntity())
                                emit("${contact.firstName} successfully added to server!")
                            }
                        } else emit(gsonConverter(response))
                    }

                    ContactState.OFFLINE_DELETED.value -> {
                        val response = contactApi.deleteContact(it.id)
                        if (response.isSuccessful) {
                            response.body()?.let { _ ->
                                contactDao.deleteContact(it)
                                emit("${it.firstName} successfully deleted to server!")
                            }
                        } else emit(gsonConverter(response))
                    }
                }
            }
            if (contactDao.getUnSyncContacts().isEmpty()) {
                val response = contactApi.getAllContacts()
                if (response.isSuccessful) {
                    response.body()?.let {
                        contactDao.clearContacts()
                        contactDao.addAllContact(it.mapToEntity())
                        emit("All data updated!")
                    }
                } else emit(gsonConverter(response))
            }
        } else emit("No internet!")
    }

    override fun getContactById(id: Int): Flow<ContactEntity?> =
        flow { emit(contactDao.getContactById(id)) }

    private fun gsonConverter(response: Response<*>): String {
        if (response.errorBody() == null) return ""
        var messageResponse = ErrorResponse("Something wrong!!! code:${response.code()}")
        if (response.code() == 406) {
            messageResponse =
                gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
        }

        return messageResponse.message
    }
}