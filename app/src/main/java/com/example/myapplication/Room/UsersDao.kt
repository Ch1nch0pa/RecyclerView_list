package com.example.myapplication.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.User

@Dao
interface UsersDao {

    @Insert(entity = UsersDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertNewUserData(user: UsersDbEntity)

    @Query("SELECT users.id, first_name, last_name, email, avatar FROM users")
    fun getAllUsersData() : MutableList<User>

    @Query("DELETE FROM users WHERE id = :userID")
    fun deleteUserById(userID: Long)
}