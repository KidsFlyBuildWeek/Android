package com.ali.kidsfly.ui

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.ali.kidsfly.R
import com.ali.kidsfly.TripApi
import com.ali.kidsfly.model.UserProfile
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference


class RegisterActivity : AppCompatActivity() {

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
                PostUserProfileAsyncTask(this).execute(user)
            } else{
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
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
                    res.enqueue(object: Callback<UserProfile>{
                        override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                            Toast.makeText(it, "User profile upload failed", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                            response.body()?.let{
                                act.get()?.let{activity->
                                    val intent = Intent(activity as Context, HomepageActivity::class.java)
                                    intent.putExtra("User", it)
                                    activity.startActivity(intent)
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}
