package com.ali.kidsfly.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ali.kidsfly.R
import com.ali.kidsfly.TripApi
import com.ali.kidsfly.model.UserProfile
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference


class RegisterActivity : AppCompatActivity(), Callback<UserProfile> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
                TripApi.getTripApiCall().postUserProfile(user).enqueue(this)
            } else{
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onFailure(call: Call<UserProfile>, t: Throwable) {
        Toast.makeText(this, "User profile upload failed", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
        response.body()?.let{
            val intent = Intent(this, HomepageActivity::class.java)
            intent.putExtra("User", it)
            startActivity(intent)
        }
    }

    class PostUserProfileAsyncTask(activity: RegisterActivity) : AsyncTask<UserProfile, Void, Call<UserProfile>>() {
        private val act = WeakReference(activity)

        override fun doInBackground(vararg p0: UserProfile?): Call<UserProfile> {
            return TripApi.getTripApiCall().postUserProfile(p0[0]!!)
        }

        override fun onPostExecute(result: Call<UserProfile>?) {
            result?.let{ res->
                act.get()?.let{
                    res.enqueue(it)
                }
            }
        }
    }
}
