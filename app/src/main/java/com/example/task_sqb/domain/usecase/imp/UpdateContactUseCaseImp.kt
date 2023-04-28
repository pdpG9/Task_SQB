package com.example.task_sqb.domain.usecase.imp

import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.repository.ContactRepository
import com.example.task_sqb.domain.usecase.UpdateContactUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateContactUseCaseImp @Inject constructor(private val repository: ContactRepository) :
    UpdateContactUseCase {
    override fun execute(contactEntity: ContactEntity):Flow<String> = repository.updateContact(contactEntity)
}