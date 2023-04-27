package com.example.midas.room.repository

import com.example.midas.room.UserInfo
import com.example.midas.room.UserInfoDao
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class UserInfoTableRepo(private val userInfoDao: UserInfoDao) {

    fun insertUser(userInfo : UserInfo) : Single<Long>{
        return userInfoDao.insertUser(userInfo)
    }

    fun fetchUser(number : String) : Single<UserInfo> {
        return userInfoDao.fetchUser(number)
    }
    fun fetchUserById(id : Int) : Single<UserInfo> {
        return userInfoDao.fetchUser(id)
    }
    fun updateUser(
        number: String,
        userPassword: String?,
                   userName: String?,
                   userEmail: String?,
                   isRegistered : Boolean) : Single<Int> {
        return userInfoDao.updateUser(number, userPassword, userName, userEmail, isRegistered)
    }
}
