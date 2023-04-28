package com.example.task_sqb.data.network.api

import com.example.task_sqb.data.network.request.AddContactRequest
import com.example.task_sqb.data.network.request.DeleteContactRequest
import com.example.task_sqb.data.network.request.UpdateContactRequest
import com.example.task_sqb.data.network.response.ContactResponse
import com.example.task_sqb.data.network.response.ContactResponseList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ContactApi {

    @GET("/api/v1/contact")
    suspend fun getAllContacts(): Response<ContactResponseList>

    @POST("/api/v1/contact")
    suspend fun addContact(@Body contact: AddContactRequest): Response<ContactResponse>

    @PUT("/api/v1/contact")
    suspend fun updateContact(@Body contact: UpdateContactRequest): Response<ContactResponse>

    @DELETE("/api/v1/contact")
    suspend fun deleteContact(@Query("id") id: Int): Response<DeleteContactRequest>

}