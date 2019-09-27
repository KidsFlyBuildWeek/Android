package com.ali.kidsfly.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.ali.kidsfly.R
import com.ali.kidsfly.adapter.TripListAdapter
import com.ali.kidsfly.model.DownloadedUserProfile
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.model.UserProfile
import com.ali.kidsfly.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.fragment_current_trips.*

class HomepageActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var userViewModel: UserViewModel
    lateinit var currentTripsAdapter: TripListAdapter

    companion object {
        lateinit var user: UserProfile //this is the current user profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(findViewById(R.id.top_toolbar))

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        user = intent.getSerializableExtra("User")!! as UserProfile

        setTripsAsObservable()
        currentTripsAdapter = TripListAdapter(userViewModel.getCurrentTrips())

        setupRecyclerView()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavigation()
        setupSideNavigation()
        setupActionBar()

        side_navigation.getHeaderView(0).findViewById<TextView>(R.id.tv_user).text = "${user.username}\n\n${user.name}"
    }

    //will be able to observe any changes to the list in user if its modified
    private fun setTripsAsObservable() {
        userViewModel.getCurrentUserTripsAsLiveData(user).observe(this,
            object: Observer<MutableList<Trip>>{
                override fun onChanged(t: MutableList<Trip>?) {
                    currentTripsAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun setupBottomNavigation(){
        NavigationUI.setupWithNavController(bottom_nav, navController)
    }

    private fun setupSideNavigation(){
        NavigationUI.setupWithNavController(side_navigation, navController)
    }

    private fun setupActionBar(){
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawer_layout)
    }

    override fun onStop() {
        //update the api regarding the user profile
        super.onStop()
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.apply{
            adapter = currentTripsAdapter
            layoutManager = manager
        }
    }
}
