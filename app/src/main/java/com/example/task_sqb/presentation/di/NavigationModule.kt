package com.example.task_sqb.presentation.di

import com.example.task_sqb.presentation.navigation.AppNavigationManager
import com.example.task_sqb.presentation.navigation.AppNavigator
import com.example.task_sqb.presentation.navigation.NavigationHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun bindAppNavigator(imp:AppNavigationManager): AppNavigator
    @[Binds Singleton]
    fun bindNavigationHandler(imp:AppNavigationManager):NavigationHandler
}