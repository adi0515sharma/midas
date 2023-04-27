package com.example.midas.Activitys

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.midas.R
import com.example.midas.Utils.PrefferenceStorage
import com.example.midas.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    var firstFragment = HomeFragment()
    var aboutUsFragment = AboutUsFragment()
    var shouldGoBack : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flFragment, firstFragment)
                        .commit();
                    true
                }
                R.id.person -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flFragment, aboutUsFragment)
                        .commit();
                    true
                }

                else -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flFragment, firstFragment)
                        .commit();
                    true
                }
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.home

        binding.logout.setOnClickListener {

            val dialog = Dialog(this)
            dialog.setContentView(R.layout.logout_dialog)
            val yes : Button = dialog.findViewById<Button>(R.id.yes)
            yes.setOnClickListener {
                PrefferenceStorage.getInstance(this).removeCurrentUser()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            val no : Button = dialog.findViewById<Button>(R.id.no)
            no.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }
    }

    override fun onBackPressed() {

        if(!shouldGoBack){
            Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show()
            shouldGoBack = true
            Handler().postDelayed(Runnable {
                shouldGoBack = false
            }, 1000)
        }
        else{
            super.onBackPressed()
        }

    }

}