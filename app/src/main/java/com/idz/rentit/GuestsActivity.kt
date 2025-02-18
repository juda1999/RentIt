package com.idz.rentit

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.idz.rentIt.R

class GuestsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guests)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.guests_nav_graph) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)
//        Firebase.initialize(context = this)
//        Firebase.appCheck.installAppCheckProviderFactory(
//            PlayIntegrityAppCheckProviderFactory.getInstance(),
//        )
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
