package com.example.midas.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.midas.Utils.PrefferenceStorage
import com.example.midas.databinding.ActivityMainBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler().postDelayed(Runnable {
            val user : Int? = isUserExist()
            if(user!=null && user > -1){
                startActivity(Intent(this, HomeActivity::class.java))
            }
            else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)

    }

    public fun isUserExist() : Int? {
        val prefferenceStorage : PrefferenceStorage = PrefferenceStorage.getInstance(this);
        val id : Int? = prefferenceStorage.getCurrentUser()
        return id

    }

}