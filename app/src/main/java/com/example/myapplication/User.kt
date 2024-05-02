package com.example.myapplication

data class DataList(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: MutableList<User>
)
data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)
