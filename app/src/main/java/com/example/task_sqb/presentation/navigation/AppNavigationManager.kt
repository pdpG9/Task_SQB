package com.example.task_sqb.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationManager @Inject constructor():AppNavigator,NavigationHandler{
    override val navigationFlow = MutableSharedFlow<NavController.()->Unit>()
    private suspend fun navigate(bloc:NavController.()->Unit){ navigationFlow.emit(bloc) }
    override suspend fun back() = navigate { popBackStack() }
    override suspend fun navigateTo(direction: NavDirections)  = navigate { navigate(direction) }
}