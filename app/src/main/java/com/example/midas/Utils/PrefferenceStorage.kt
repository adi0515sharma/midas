package com.example.midas.Utils

import android.content.Context
import android.content.SharedPreferences




class PrefferenceStorage {


    companion object{
        var PREFFERENCE = "LogInfo"
        var USER_ID = "USER_ID"
        var prefferenceStorage : PrefferenceStorage? = null;
        var sharedpreferences: SharedPreferences? = null;

        public fun getInstance(context: Context) : PrefferenceStorage{
            sharedpreferences = context.getSharedPreferences(PREFFERENCE, Context.MODE_PRIVATE)
            if(prefferenceStorage==null){
                prefferenceStorage = PrefferenceStorage()
            }
            return prefferenceStorage!!
        }
    }


    public fun getCurrentUser() : Int?{
        var userId : Int? = null
        if(sharedpreferences?.contains(USER_ID) == true){
            userId = sharedpreferences?.getInt(USER_ID, -1)
        }
        return userId
    }

    public fun setCurrentUser(currentUserId : Int?) {
        val editor = sharedpreferences?.edit()
        editor?.putInt(USER_ID, currentUserId!!)
        editor?.commit()
    }

    public fun removeCurrentUser() {
        val editor = sharedpreferences?.edit()
        editor?.remove(USER_ID)
        editor?.commit()
    }
}