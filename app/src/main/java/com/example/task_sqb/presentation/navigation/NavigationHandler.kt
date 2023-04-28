package com.example.task_sqb.presentation.navigation

import androidx.navigation.NavDirections

interface NavigationHandler {
    suspend fun back()
    suspend fun navigateTo(direction:NavDirections)
}