package com.example.task_sqb.domain.usecase

import com.example.task_sqb.data.local.room.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

interface UpdateContactUseCase {
    fun execute(contactEntity: ContactEntity):Flow<String>
}