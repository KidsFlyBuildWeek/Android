package com.ali.kidsfly.ui

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ali.kidsfly.R
import com.ali.kidsfly.api.UserApi
import com.ali.kidsfly.model.UserProfile
import com.ali.kidsfly.repo.UserRepo
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference


class RegisterActivity : AppCompatActivity() {

    private lateinit var userRepo: UserRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        userRepo = UserRepo(this)

        btn_submit.setOnClickListener{
            val fullname = et_full_name.text.toString()
            val password = et_password.text.toString()
            val phonenumber = et_phone_number.text.toString()
            val email = et_email.text.toString()
            val username = et_username.text.toString()
            val address = et_address.text.toString()

            if(fullname != "" && address != "" && password != "" && phonenumber != "" && email != "" &&
                password == et_confirm_password.text.toString() && username != ""){
                val user = UserProfile(username, password, phonenumber, email, fullname, address)
                progress_bar.visibility = View.VISIBLE
                userRepo.registerUserProfile(user) //registers entered information as a user on the endpoint
            } else{
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
