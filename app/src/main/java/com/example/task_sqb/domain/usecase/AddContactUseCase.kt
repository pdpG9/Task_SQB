package com.example.task_sqb.domain.usecase

import com.example.task_sqb.data.network.request.AddContactRequest
import kotlinx.coroutines.flow.Flow

interface AddContactUseCase {
    fun execute(addContactRequest: AddContactRequest): Flow<String>
}