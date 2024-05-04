package com.example.myapplication

import com.example.myapplication.Retrofit.UsersApi
import com.example.myapplication.Room.UsersDao
import com.example.myapplication.Room.UsersDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(private val usersDao: UsersDao) {

    suspend fun insertNewUserData(usersApi: UsersApi) = withContext(Dispatchers.IO) {
        val data = usersApi.getUsers()
        data.body()?.let {
            val users = it.data.map { user ->
                UsersDbEntity(
                    id = user.id,
                    firstName = user.first_name,
                    lastName = user.last_name,
                    email = user.email,
                    avatar = user.avatar
                )
            }
            users.forEach { user ->
                usersDao.insertNewUserData(user)
            }
        }
    }

suspend fun getAllUserData(): MutableList<User> {
    return withContext(Dispatchers.IO) {
        return@withContext usersDao.getAllUsersData()
    }
}

suspend fun removeUserByID(id: Long) {
    withContext(Dispatchers.IO) {
        usersDao.deleteUserById(id)
    }
}
}