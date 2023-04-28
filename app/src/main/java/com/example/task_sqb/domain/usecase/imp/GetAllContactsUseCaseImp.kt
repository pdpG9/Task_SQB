package com.example.task_sqb.domain.usecase.imp

import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.repository.ContactRepository
import com.example.task_sqb.domain.usecase.GetAllContactsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllContactsUseCaseImp @Inject constructor(private val repository: ContactRepository) :
    GetAllContactsUseCase {
    override fun execute(): Flow<List<ContactEntity>> = repository.getAllContacts()
}