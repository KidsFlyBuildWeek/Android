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
import com.ali.kidsfly.model.UserProfile
import com.ali.kidsfly.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

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

                //registers entered information as a user on the endpoint gets back a number
                userViewModel.registerUserProfile(user).observe(this, object: Observer<Int>{
                    override fun onChanged(t: Int?) {
                        t?.let{
                            userViewModel.getUserProfile(it).observe(this@RegistrationActivity,
                                object: Observer<DownloadedUserProfile> {
                                    override fun onChanged(t: DownloadedUserProfile?) {
                                        t?.let {
                                            progress_bar.visibility = View.GONE
                                            if (it.username == "") {
                                                Toast.makeText(this@RegistrationActivity, "Sign In After Registration Failed", Toast.LENGTH_SHORT).show()
                                            } else {
                                                val intent = Intent(this@RegistrationActivity, HomepageActivity::class.java)
                                                intent.putExtra("User", it)
                                                startActivity(intent)
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                })

            } else{
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
