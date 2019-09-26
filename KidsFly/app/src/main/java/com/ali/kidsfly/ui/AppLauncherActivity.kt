package com.ali.kidsfly.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ali.kidsfly.R
import com.ali.kidsfly.TripApi
import com.ali.kidsfly.model.DownloadedUserProfile
import kotlinx.android.synthetic.main.activity_app_launcher.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class AppLauncherActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_launcher)

        register.setOnClickListener {
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        signIn.setOnClickListener{
            progress_bar.visibility = View.VISIBLE
            GetUserSignInAsyncTask(this).execute(2) // just hard-code it for now
        }
    }

    class GetUserSignInAsyncTask(activity: AppCompatActivity) : AsyncTask<Int, Void, Call<DownloadedUserProfile>>(){
        private val act = WeakReference(activity)

        override fun doInBackground(vararg p0: Int?): Call<DownloadedUserProfile> {
            return TripApi.getTripApiCall().getUserProfileInformation(p0[0]!!)
        }

        override fun onPostExecute(result: Call<DownloadedUserProfile>?) {
            result?.let{
                it.enqueue(object: Callback<DownloadedUserProfile> {
                    override fun onFailure(call: Call<DownloadedUserProfile>, t: Throwable) {
                        act.get()?.let{
                            Toast.makeText(it, "Could not get this user profile", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResponse(call: Call<DownloadedUserProfile>, response: Response<DownloadedUserProfile>) {
                        act.get()?.let{ x->
                            act.get()?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE
                            response.body()?.let{
                                val intent= Intent(x as Context, HomepageActivity::class.java)
                                intent.putExtra("User", it)
                                x.startActivity(intent)
                            }
                        }
                    }
                })
            }
        }
    }
}
