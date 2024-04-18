package com.example.myapplication

import net.datafaker.Faker
import java.util.*

class ListUsers {
    private var users = mutableListOf<User>()
    init {
        val faker = Faker(Locale("ru"))
        users = (1..120).map {
            var name = faker.name().nameWithMiddle()
            val words = name.split(" ")
            name = words[2] + " " + words[0]
            User(
                name = name,
                companyName = faker.company().name(),
                image = faker.avatar().image()
            )
        }.toMutableList()
    }
    fun getUsers(): List<User> = users
}