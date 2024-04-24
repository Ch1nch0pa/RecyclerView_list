package com.example.myapplication

import net.datafaker.Faker
import java.util.*
typealias UserListener = (persons: List<User>) -> Unit
class ListUsers {
    private var users = mutableListOf<User>()
    private var listeners = mutableListOf<UserListener>()
    init {
        val faker = Faker(Locale("ru"))
        users = (1..120).map {
            var name = faker.name().nameWithMiddle()
            val words = name.split(" ")
            name = words[2] + " " + words[0]
            User(
                id = it.toLong(),
                name = name,
                companyName = faker.company().name(),
                image = "https://api.dicebear.com/8.x/pixel-art/png?seed=${name}"
            )
        }.toMutableList()
    }
    fun getUsers(): List<User> = users

    fun moveUser(user: User, pos: Int){
        val index = users.indexOf(user)
        if ((index>0) && (pos > 0) || (index<users.count()-1) && (pos < 0)) {
            val temp = users.toMutableList()
            temp[index] = users[index-pos]
            temp[index - pos] = user
            users = temp
        }
        notifyChanges()
    }
    fun deleteUser(user: User)
    {
        val temp = users.toMutableList()
        temp.remove(user)
        users = temp
        notifyChanges()
    }
    fun addListener(listener: UserListener) {
        listeners.add(listener)
        listener.invoke(users)
    }
    fun removeListener(listener: UserListener) {
        listeners.remove(listener)
        listener.invoke(users)
    }
    private fun notifyChanges() = listeners.forEach {it.invoke(users)}
}