package com.example.task_sqb.domain.usecase

import kotlinx.coroutines.flow.Flow

interface SyncDataUseCase {
    fun execute():Flow<String>
}