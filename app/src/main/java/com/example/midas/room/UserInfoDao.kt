package com.example.midas.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface UserInfoDao {

    @Insert
    fun insertUser(userInfo: UserInfo) : Single<Long>;

    @Query("SELECT * FROM userinfo WHERE userMobileNo = :number")
    fun fetchUser(number: String) : Single<UserInfo>;

    @Query("SELECT * FROM userinfo WHERE userId = :id")
    fun fetchUser(id: Int) : Single<UserInfo>;

    @Query("UPDATE userinfo SET userPassword =:userPassword, userName = :userName ,userEmail = :userEmail, isRegistered = :isRegistered WHERE userMobileNo = :number")
    fun updateUser(
        number: String,
        userPassword: String?,
        userName: String?,
        userEmail: String?,
        isRegistered : Boolean) : Single<Int>;

}