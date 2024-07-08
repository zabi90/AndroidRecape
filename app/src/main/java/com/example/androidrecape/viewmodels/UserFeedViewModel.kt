package com.example.androidrecape.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidrecape.models.User

class UserFeedViewModel : ViewModel() {

    private val users : MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>()
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    fun loadUsers() {
        // Do an asynchronous operation to fetch users.
       val usersList = listOf(
           User(name = "Zohaib", description = "This is cool "+Math.random()),
           User(name = "Zohaib2", description = "This is cool "+Math.random()),
           User(name = "Zohaib2", description = "This is cool "+Math.random()),
           User(name = "Zohaib2", description = "This is cool "+Math.random())
       )
        users.value = usersList
    }
}