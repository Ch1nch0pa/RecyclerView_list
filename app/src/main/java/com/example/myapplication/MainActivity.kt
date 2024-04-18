package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var itemBinding: ItemBinding
    private lateinit var adapter: UserAdapter
    private val listUsers: ListUsers // Объект PersonService
        get() = (applicationContext as App).listUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        itemBinding = ItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this) // LayoutManager
        adapter = UserAdapter() // Создание объекта
        adapter.data = listUsers.getUsers() // Заполнение данными
        userViewModel.init(adapter.data)

        binding.recyclerView.layoutManager = manager // Назначение LayoutManager для RecyclerView
        binding.recyclerView.adapter = adapter

        userViewModel.userLiveList.observe(this) {
            userViewModel.userLiveList.value?.let { it1 -> adapter.updateList(it1) }
        }

        adapter.onChangePositionUp = {
            userViewModel.moveUser(it, 1)
        }

        adapter.onChangePositionDown = {
            userViewModel.moveUser(it, -1)
        }

        adapter.onDelete = {
            userViewModel.deleteUser(it)
        }

        adapter.onItemClick = {
            val text = "${it.name}: ${it.companyName}"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

    }


}