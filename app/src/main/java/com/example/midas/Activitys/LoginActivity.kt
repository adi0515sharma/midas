package com.example.midas.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.midas.R
import com.example.midas.Utils.PrefferenceStorage
import com.example.midas.Utils.Verifier
import com.example.midas.ViewModels.LoginActivityViewModel
import com.example.midas.ViewModels.RegistertationActivityViewModel
import com.example.midas.databinding.ActivityLoginBinding
import com.example.midas.room.UserInfo

class LoginActivity : AppCompatActivity() {

    lateinit var loginActivityViewModel : LoginActivityViewModel
    lateinit var activityLoginBinding: ActivityLoginBinding
    var userInfo : UserInfo?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
        loginActivityViewModel = ViewModelProvider(this)[LoginActivityViewModel::class.java]


        val newUserObserver = Observer<Long> { it->
            if(!(it.equals(-1))){
                Toast.makeText(this, "please register your self", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, RegistrationActivity::class.java).apply {
                    putExtra("userInfo", userInfo)
                })
            }
            else{
                Toast.makeText(this, "couldn't able to register user", Toast.LENGTH_LONG).show()
            }
        }
        loginActivityViewModel.newRecordId.observe(this, newUserObserver)

        val nameObserver = Observer<UserInfo?> { newName ->
            // Update the UI, in this case, a TextView.
            activityLoginBinding.login.isEnabled = true;
            activityLoginBinding.idPhoneNo.isEnabled = true;

            if(newName == null){
                userInfo = UserInfo(
                    userMobileNo = activityLoginBinding.idPhoneNo.text.toString(),
                    isRegistered = false,
                    userEmail = null,
                    userPassword = null,
                    userName = null)
                loginActivityViewModel.insertNewUser(userInfo!!)
            }
            else if(newName!=null && (newName.isRegistered==null || newName.isRegistered==false)) {
                Toast.makeText(this, "please register your self", Toast.LENGTH_LONG).show()

                startActivity(Intent(this, RegistrationActivity::class.java).apply {
                    putExtra("userInfo", newName)
                })

            }
            else if(newName!=null && newName.isRegistered!=null && newName.isRegistered){
                userInfo = newName
                activityLoginBinding.passwordLl.visibility  = View.VISIBLE
                activityLoginBinding.phoneNumberLl.visibility = View.GONE
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        loginActivityViewModel.liveUser.observe(this, nameObserver)

        activityLoginBinding.login.setOnClickListener {
            submitMobileNo()
        }

        activityLoginBinding.password.setOnClickListener {
            submitPassword()
        }
    }

    fun submitMobileNo(){
        if(Verifier.isPhoneNumberValid(activityLoginBinding.idPhoneNo.text.toString())){
            activityLoginBinding.login.isEnabled = false;
            activityLoginBinding.idPhoneNo.isEnabled = false;
            loginActivityViewModel.getUser(activityLoginBinding.idPhoneNo.text.toString())
        }
        else{
            activityLoginBinding.idPhoneNo.error = "Please provide proper mobile no"
        }
    }

    fun submitPassword(){
        if(Verifier.isPasswordValid(activityLoginBinding.idPasswordNo.text.toString())){

            val password  :String = activityLoginBinding.idPasswordNo.text.toString()
            if(password == userInfo?.userPassword){

                PrefferenceStorage.getInstance(this).setCurrentUser(userInfo?.userId)

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "Wrong Password", Toast.LENGTH_LONG).show()
            }
        }
        else{
            activityLoginBinding.idPasswordNo.error = "Please enter 4 digits password"
        }
    }

    override fun onBackPressed() {
        if(activityLoginBinding.passwordLl.visibility == View.VISIBLE){
            activityLoginBinding.idPasswordNo.setText("")

            activityLoginBinding.passwordLl.visibility = View.GONE
            activityLoginBinding.phoneNumberLl.visibility = View.VISIBLE
            userInfo = null
        }
        else{
            super.onBackPressed()
        }
    }
}