package com.example.myapplication

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {
    @GET("/api/users?per_page=500")
    suspend fun getUsers() : Response<DataList>

    @DELETE("/api/users/{ID}")
    suspend fun deleteUser(@Path("ID") userID: Int): Response<ResponseBody>
}

object RetrofitClient {

    val baseUrl = "https://reqres.in/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}