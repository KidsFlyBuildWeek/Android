package com.ali.kidsfly.repo

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ali.kidsfly.R
import com.ali.kidsfly.api.UserApi
import com.ali.kidsfly.dao.TripDao
import com.ali.kidsfly.database.TripDatabase
import com.ali.kidsfly.model.DownloadedUserProfile
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.model.UserProfile
import com.ali.kidsfly.ui.AppLauncherActivity
import com.ali.kidsfly.ui.HomepageActivity
import com.ali.kidsfly.ui.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class UserRepo(context: Context): TripDao {

    private val contxt = context.applicationContext

    //call this from within AppLauncherActivity, this will get the user profile, and then switch the screen to the homepage when done, passing
    //the downloaded user profile through to that activity
    fun getUserProfile(parentId: Int){
        GetUserSignInAsyncTask(contxt as AppLauncherActivity).execute(parentId)
    }

    //call this from within the RegistrationActivity this will post the user profile to the API, and will also go to the homepage after
    // fetching the ID of the just created user profile
    fun registerUserProfile(user: UserProfile){
        PostUserProfileAsyncTask(contxt as RegisterActivity).execute(user)
    }

    //ONLY UPDATE THE API WITH THE TRIP INFORMATION WHEN THE USER SIGNS OUT OR the activity is destroyed with OnStop (user gets rid of app screen)

    fun createCurrentTrip(trip: Trip){ //add it to UserProfile trips object then update it in the api at a certain point

    }

    fun deleteCurrentTrip(trip: Trip){ //delete this trip from the user profile and eventually update in the api

    }

    fun updateCurrentTrip(trip: Trip){ //updates a current trip inside user profile and eventually update in the api

    }

    //pass in the user profile, it will wrap the trips mutable list in live data and return it
    fun getAllCurrentTripsAsLiveData(user: UserProfile): MutableLiveData<MutableList<Trip>> {
        return MutableLiveData<MutableList<Trip>>((user as DownloadedUserProfile).trips)
    }

    /*
    The functions below access a room database that stores and retrieves information on trips that are past!
     */

    override fun createSavedTripEntry(trip: Trip) {
        savedTripsDatabase.tripDao().createSavedTripEntry(trip)
    }

    override fun getAllSavedTrips(): LiveData<MutableList<Trip>> {
        return savedTripsDatabase.tripDao().getAllSavedTrips()
    }

    private val savedTripsDatabase by lazy{
        Room.databaseBuilder(
            contxt,
            TripDatabase::class.java,
            "entry_database2"
        ).fallbackToDestructiveMigration().build()
    }

    class PostUserProfileAsyncTask(activity: RegisterActivity) : AsyncTask<UserProfile, Void, Call<Unit>>() {
        private val act = WeakReference(activity)

        override fun doInBackground(vararg p0: UserProfile?): Call<Unit> {
            return UserApi.getUserApiCall().postUserProfile(p0[0]!!)
        }

        override fun onPostExecute(result: Call<Unit>?) {
            result?.let { res ->
                act.get()?.let {
                    it.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE

                    res.enqueue(object : Callback<Unit> {
                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Toast.makeText(it, "User profile upload failed", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            val s = response.headers().get("Location")
                            val i = s!!.substring(s.lastIndexOf("/") + 1).toInt()
                            GetUserSignInAsyncTask(it).execute(i) //gets the post that's just created
                        }
                    })
                }
            }
        }
    }

    class GetUserSignInAsyncTask(activity: AppCompatActivity) : AsyncTask<Int, Void, Call<DownloadedUserProfile>>(){
        private val act = WeakReference(activity)

        override fun doInBackground(vararg p0: Int?): Call<DownloadedUserProfile> {
            return UserApi.getUserApiCall().getUserProfileInformation(p0[0]!!)
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
                                val intent = Intent(x as Context, HomepageActivity::class.java)
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