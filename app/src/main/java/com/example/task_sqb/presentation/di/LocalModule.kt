package com.example.task_sqb.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.task_sqb.data.local.room.ContactDatabase
import com.example.task_sqb.data.local.room.dao.ContactDao
import com.example.task_sqb.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @[Provides Singleton]
    fun provideContactDatabase(@ApplicationContext context: Context):ContactDatabase =
   Room.databaseBuilder(context,ContactDatabase::class.java,DATABASE_NAME).build()
    @[Provides Singleton]
    fun provideContactDao(contactDatabase: ContactDatabase):ContactDao = contactDatabase.contactDao()
}