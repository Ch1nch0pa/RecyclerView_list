package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemBinding: ItemBinding
    private lateinit var adapter: UserAdapter
    private val listUsers: ListUsers
        get() = (applicationContext as App).listUsers

    private val listener: UserListener = {adapter.data = it}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        itemBinding = ItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this)
        adapter = UserAdapter(object : UserActionListener {
            override fun onUserInfo(user: User) {
                val text = "${user.name}: ${user.companyName}"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            override fun onUserMove(user: User, position: Int)  = listUsers.moveUser(user, position)
            override fun onUserDelete(user: User) = listUsers.deleteUser(user)
        })
        listUsers.addListener(listener)
        adapter.data = listUsers.getUsers()

        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val checkable = menu.findItem(R.id.menu_item_theme)
        checkable.setChecked(isDarkTheme())
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_theme -> {
                val isChecked = item.isChecked
                if (isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                item.setChecked(!isChecked)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun Context.isDarkTheme(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES }
}