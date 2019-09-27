package com.ali.kidsfly.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ali.kidsfly.R
import com.ali.kidsfly.model.DownloadedUserProfile
import com.ali.kidsfly.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var userViewModel: UserViewModel

    companion object {
        lateinit var user: DownloadedUserProfile //this is the current user profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(findViewById(R.id.top_toolbar))

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        user = intent.getSerializableExtra("User")!! as DownloadedUserProfile

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
