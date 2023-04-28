package com.example.task_sqb.data.network.request

data class UpdateContactRequest(
    val id:Int,
    val firstName:String,
    val lastName:String,
    val phone:String,
)
