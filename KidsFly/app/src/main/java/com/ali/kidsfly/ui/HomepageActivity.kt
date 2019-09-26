package com.ali.kidsfly.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ali.kidsfly.R
import com.ali.kidsfly.TripViewModel
import com.ali.kidsfly.model.UserProfile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var tripViewModel: TripViewModel

    companion object {
        lateinit var user: UserProfile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_homepage)
        setSupportActionBar(findViewById(R.id.top_toolbar))

        user = intent.getSerializableExtra("User")!! as UserProfile

        tripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavigation()
        setupSideNavigation()
        setupActionBar()

        side_navigation.getHeaderView(0).findViewById<TextView>(R.id.tv_user).text = "${user.username}\n\n${user.name}"
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
}
