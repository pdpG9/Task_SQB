package com.example.task_sqb.domain.usecase.imp

import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.repository.ContactRepository
import com.example.task_sqb.domain.usecase.GetContactByIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactByIdUseCaseImp @Inject constructor(private val repository: ContactRepository):GetContactByIdUseCase {
    override fun execute(id: Int): Flow<ContactEntity?> = repository.getContactById(id)
}