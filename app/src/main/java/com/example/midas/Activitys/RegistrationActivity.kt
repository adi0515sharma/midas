package com.example.midas.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.midas.R
import com.example.midas.Utils.PrefferenceStorage
import com.example.midas.Utils.Verifier
import com.example.midas.ViewModels.RegistertationActivityViewModel
import com.example.midas.databinding.ActivityRegistrationBinding
import com.example.midas.room.UserInfo

class RegistrationActivity : AppCompatActivity() {

    lateinit var registertationActivityViewModel : RegistertationActivityViewModel
    lateinit var activityRegistrationBinding: ActivityRegistrationBinding
    var currentUserId : Int? = null
    var userInfo : UserInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegistrationBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(activityRegistrationBinding.root)
        registertationActivityViewModel = ViewModelProvider(this)[RegistertationActivityViewModel::class.java]

        userInfo = intent.getParcelableExtra<UserInfo>("userInfo")


        registertationActivityViewModel.liveUser.observe(this, Observer<UserInfo?>{
            PrefferenceStorage.getInstance(this).setCurrentUser(it?.userId)
            Toast.makeText(this, "Registration Successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        })

        registertationActivityViewModel.updateRecordId.observe(this, Observer<Int> { it->
            Log.e("TAG", it.toString())
            if(it!=-1){
                registertationActivityViewModel.getUser(userInfo?.userMobileNo!!)
            }
            else{
                Toast.makeText(this, "couldn't able to register user", Toast.LENGTH_LONG).show()
            }
        })


        activityRegistrationBinding.registered.setOnClickListener {
            handleRegistration()
        }
    }

    public fun handleRegistration(){


        if(!Verifier.isUserNameCorrect(activityRegistrationBinding.idNameNo.text.toString())){
            activityRegistrationBinding.idNameNo.error = "Please provide proper name"
            return
        }


        if(!Verifier.isEmailCorrect(activityRegistrationBinding.idEmailNo.text.toString())){
            activityRegistrationBinding.idEmailNo.error = "Please provide proper email id"
            return
        }


        if(!Verifier.isPasswordValid(activityRegistrationBinding.idPasswordNo.text.toString())){
            activityRegistrationBinding.idPasswordNo.error = "Please enter 4 digits password"
            return
        }


        userInfo?.userEmail = activityRegistrationBinding.idEmailNo.text.toString()
        userInfo?.userName = activityRegistrationBinding.idNameNo.text.toString()
        userInfo?.userPassword = activityRegistrationBinding.idPasswordNo.text.toString()
        userInfo?.isRegistered = true
        currentUserId = userInfo?.userId
        registertationActivityViewModel.updateUser(userInfo!!)
    }

}