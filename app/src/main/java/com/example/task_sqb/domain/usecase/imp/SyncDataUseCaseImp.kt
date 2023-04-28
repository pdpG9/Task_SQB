package com.example.task_sqb.domain.usecase.imp

import com.example.task_sqb.data.repository.ContactRepository
import com.example.task_sqb.domain.usecase.SyncDataUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncDataUseCaseImp @Inject constructor(private val repository: ContactRepository):SyncDataUseCase {
    override fun execute(): Flow<String> = repository.syncData()
}