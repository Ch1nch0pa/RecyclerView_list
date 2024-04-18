package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val userList = ListUsers()
    private val _userLiveList = MutableLiveData<List<User>>()
    val userLiveList: LiveData<List<User>>
        get() = _userLiveList

    fun init(data: List<User>) {
        _userLiveList.value = data
    }

    fun moveUser(user: User, pos: Int){
        val users = _userLiveList.value ?: return
        val index = users.indexOf(user)
        if ((index>0) && (pos > 0) || (index<users.count()-1) && (pos < 0)) {
            val temp = users.toMutableList()
            temp[index] = users[index-pos]
            temp[index - pos] = user
            _userLiveList.value = temp
        }
    }
    fun deleteUser(user: User)
    {
        val users = _userLiveList.value ?: return
        val temp = users.toMutableList()
        temp.remove(user)
        _userLiveList.value = temp
    }
}
