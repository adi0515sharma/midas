package com.example.midas.ViewModels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.midas.room.UserInfo
import com.example.midas.room.UserInfoDao
import com.example.midas.room.UserInfoDatabase
import com.example.midas.room.repository.UserInfoTableRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RegistertationActivityViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var userInfoTableRepo : UserInfoTableRepo
    val liveUser: MutableLiveData<UserInfo?> by lazy {
        MutableLiveData<UserInfo?>()
    }
    val updateRecordId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init{
        val context = getApplication<Application>().applicationContext
        val userInfoDao : UserInfoDao = UserInfoDatabase.Companion.getDatabase(context).userInfoDao()
        userInfoTableRepo = UserInfoTableRepo(userInfoDao)
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

    fun updateUser(userInfo: UserInfo){
        userInfoTableRepo.updateUser(userInfo.userMobileNo!!, userInfo.userPassword, userInfo.userName, userInfo.userEmail, userInfo.isRegistered)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                // Handle successful retrieval of user
                updateRecordId.value = it
            }, { error ->
                // Handle error retrieving user
                updateRecordId.value = -1
            })
    }
}