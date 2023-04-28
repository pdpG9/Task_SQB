package com.example.task_sqb.domain.usecase.imp

import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.repository.ContactRepository
import com.example.task_sqb.domain.usecase.DeleteContactUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteContactUseCaseImp @Inject constructor(private val repository: ContactRepository) :
    DeleteContactUseCase {
    override fun execute(contactModel: ContactEntity):Flow<String> = repository.deleteContact(contactModel)
}