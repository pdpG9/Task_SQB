package com.example.task_sqb.presentation.screens.add_contact

import androidx.lifecycle.LiveData
import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.network.request.AddContactRequest

interface AddContactViewModel {
    val showProgressFlow:LiveData<Boolean>
    val defContactFlow:LiveData<ContactEntity>
    val moveToBackFlow:LiveData<Unit>
    val messageFlow:LiveData<String>

    fun loadDefContact(id:Int)
    fun clickClose()
    fun clickSave(addContactRequest: AddContactRequest)
}