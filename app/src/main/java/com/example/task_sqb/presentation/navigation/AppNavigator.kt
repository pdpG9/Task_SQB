package com.example.task_sqb.presentation.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

interface AppNavigator {
    val navigationFlow:Flow<NavController.()->Unit>
}