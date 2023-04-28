package com.example.task_sqb.presentation.screens.main.vm

import androidx.lifecycle.LiveData
import com.example.task_sqb.data.local.room.entity.ContactEntity

interface MainViewModel {
    val contactsFlow: LiveData<LiveData<List<ContactEntity>>>
    val messageFlow: LiveData<String>
    val showProgressFlow:LiveData<Boolean>
    val showPlaceHolderFlow:LiveData<Boolean>
    val moveToAddDialogFlow:LiveData<ContactEntity?>


    fun clickAddContact()
    fun syncData()
    fun clickUpdate(contactEntity: ContactEntity)
    fun clickDelete(contactEntity: ContactEntity)
}