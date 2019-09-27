package com.ali.kidsfly.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ali.kidsfly.R
import com.ali.kidsfly.model.DownloadedUserProfile
import com.ali.kidsfly.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_app_launcher.*

class AppLauncherActivity : AppCompatActivity(){

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_launcher)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        register.setOnClickListener {
            val intent= Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        signIn.setOnClickListener{

            progress_bar.visibility = View.VISIBLE

            userViewModel.getUserProfile(2).observe(this,
                object: Observer<DownloadedUserProfile?>{
                    override fun onChanged(t: DownloadedUserProfile?) {
                        t?.let {
                            progress_bar.visibility = View.GONE
                            if (it.username == "") {
                                Toast.makeText(this@AppLauncherActivity, "Sign In Failed", Toast.LENGTH_SHORT).show()
                            } else {
                                val intent = Intent(this@AppLauncherActivity, HomepageActivity::class.java)
                                intent.putExtra("User", it)
                                startActivity(intent)
                            }
                        }
                    }
                }
            )
        }
    }
}
