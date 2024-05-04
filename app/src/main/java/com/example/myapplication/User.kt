package com.example.myapplication

import androidx.room.ColumnInfo

data class DataList(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: MutableList<User>
)
data class User(
    val id: Long,
    @ColumnInfo(name = "first_name") val first_name: String,
    @ColumnInfo(name = "last_name") val last_name: String,
    @ColumnInfo(name = "email") val email: String,
    val avatar: String
)
