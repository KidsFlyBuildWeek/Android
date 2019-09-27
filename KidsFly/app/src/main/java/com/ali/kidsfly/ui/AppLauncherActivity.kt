package com.ali.kidsfly.ui

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ali.kidsfly.R
import com.ali.kidsfly.api.UserApi
import com.ali.kidsfly.model.DownloadedUserProfile
import com.ali.kidsfly.repo.UserRepo
import kotlinx.android.synthetic.main.activity_app_launcher.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class AppLauncherActivity : AppCompatActivity(){

    private lateinit var userRepo: UserRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_launcher)
        userRepo = UserRepo(this)

        register.setOnClickListener {
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        signIn.setOnClickListener{
            progress_bar.visibility = View.VISIBLE
            userRepo.getUserProfile(2) //hard code for now
        }
    }
}
