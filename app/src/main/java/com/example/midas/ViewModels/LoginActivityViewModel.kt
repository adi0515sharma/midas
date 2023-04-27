package com.example.midas.ViewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.midas.room.UserInfo
import com.example.midas.room.UserInfoDao
import com.example.midas.room.UserInfoDatabase
import com.example.midas.room.repository.UserInfoTableRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginActivityViewModel (application: Application) : AndroidViewModel(application) {
    lateinit var userInfoTableRepo : UserInfoTableRepo
    val liveUser: MutableLiveData<UserInfo?> by lazy {
        MutableLiveData<UserInfo?>()
    }

    val newRecordId: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    init{
        val context = getApplication<Application>().applicationContext
        val userInfoDao : UserInfoDao = UserInfoDatabase.Companion.getDatabase(context).userInfoDao()
        userInfoTableRepo = UserInfoTableRepo(userInfoDao)
    }
    fun insertNewUser(userInfo : UserInfo){
        userInfoTableRepo.insertUser(userInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                // Handle successful retrieval of user
                newRecordId.value = user
            }, { error ->
                // Handle error retrieving user
                newRecordId.value = -1
            })

    }
    fun getUser(number : String){
        userInfoTableRepo.fetchUser(number)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                // Handle successful retrieval of user
                liveUser.value = user
            }, { error ->
                // Handle error retrieving user
                liveUser.value = null
            })

    }
}