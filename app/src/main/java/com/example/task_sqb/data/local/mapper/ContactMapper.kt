package com.example.task_sqb.data.local.mapper

import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.local.room.entity.ContactState
import com.example.task_sqb.data.network.request.AddContactRequest
import com.example.task_sqb.data.network.request.DeleteContactRequest
import com.example.task_sqb.data.network.request.UpdateContactRequest
import com.example.task_sqb.data.network.response.ContactResponse

fun ContactResponse.mapToEntity() =
    ContactEntity(this.id, this.firstName, this.lastName, this.phone,ContactState.ONLINE.value)

fun List<ContactResponse>.mapToEntity() = this.map { it.mapToEntity() }

fun ContactEntity.mapToUpdateRequest() = UpdateContactRequest(id, firstName, lastName, phone)
fun ContactEntity.mapToDeleteRequest() = DeleteContactRequest(id)
fun ContactEntity.mapToAddContactRequest() = AddContactRequest(this.firstName,this.lastName,this.phone)
