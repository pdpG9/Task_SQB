package com.example.task_sqb.domain.usecase.imp

import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.local.room.entity.ContactState
import com.example.task_sqb.data.network.request.AddContactRequest
import com.example.task_sqb.data.repository.ContactRepository
import com.example.task_sqb.domain.usecase.AddContactUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddContactUseCaseImp @Inject constructor(private val repository: ContactRepository) :
    AddContactUseCase {
    override fun execute(addContactRequest: AddContactRequest):Flow<String> = repository.addContact(ContactEntity(0,addContactRequest.firstName,addContactRequest.lastName,addContactRequest.phone,ContactState.OFFLINE_ADDED.value))

}