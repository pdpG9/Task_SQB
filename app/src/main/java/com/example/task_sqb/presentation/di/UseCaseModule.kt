package com.example.task_sqb.presentation.di

import com.example.task_sqb.domain.usecase.AddContactUseCase
import com.example.task_sqb.domain.usecase.DeleteContactUseCase
import com.example.task_sqb.domain.usecase.GetAllContactsUseCase
import com.example.task_sqb.domain.usecase.GetContactByIdUseCase
import com.example.task_sqb.domain.usecase.SyncDataUseCase
import com.example.task_sqb.domain.usecase.UpdateContactUseCase
import com.example.task_sqb.domain.usecase.imp.AddContactUseCaseImp
import com.example.task_sqb.domain.usecase.imp.DeleteContactUseCaseImp
import com.example.task_sqb.domain.usecase.imp.GetAllContactsUseCaseImp
import com.example.task_sqb.domain.usecase.imp.GetContactByIdUseCaseImp
import com.example.task_sqb.domain.usecase.imp.SyncDataUseCaseImp
import com.example.task_sqb.domain.usecase.imp.UpdateContactUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @[Binds Singleton]
    fun bindGetAllContactsUseCase(imp:GetAllContactsUseCaseImp):GetAllContactsUseCase

    @[Binds Singleton]
    fun bindDeleteContactUseCase(imp:DeleteContactUseCaseImp):DeleteContactUseCase

    @[Binds Singleton]
    fun bindUpdateContactUseCase(imp:UpdateContactUseCaseImp):UpdateContactUseCase

    @[Binds Singleton]
    fun bindAddContactUseCase(imp:AddContactUseCaseImp):AddContactUseCase

    @[Binds Singleton]
    fun bindGetContactByIdUseCase(imp:GetContactByIdUseCaseImp):GetContactByIdUseCase
    @[Binds Singleton]
    fun bindSyncDataUseCase(imp:SyncDataUseCaseImp): SyncDataUseCase
}