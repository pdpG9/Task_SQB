package com.example.task_sqb.presentation.di

import com.example.task_sqb.data.repository.ContactRepository
import com.example.task_sqb.data.repository.ContactRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindContactRepository(imp:ContactRepositoryImp):ContactRepository
}