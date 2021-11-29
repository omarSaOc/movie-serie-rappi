package com.oaso.movie_series_rappi.ui.popular

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.ActivityPopularBinding
import com.oaso.movie_series_rappi.ui.common.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPopularBinding
    private lateinit var connection : ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPopularBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        connection = ConnectionLiveData(application)
        connection.observe(this,::checkConnectionInternet)

    }

    private fun checkConnectionInternet(isConnected : Boolean){
        if (isConnected){
            binding.llConnection.visibility = View.GONE
        }else{
            binding.llConnection.visibility = View.VISIBLE
        }
    }
}