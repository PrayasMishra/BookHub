package com.example.bookhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.circularreveal.coordinatorlayout.CircularRevealCoordinatorLayout
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var toolbar : Toolbar
    lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var frameLayout: FrameLayout
    private lateinit var navigationView : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frameLayout = findViewById(R.id.coordinatorLayout)
        navigationView = findViewById(R.id.navigationView)
        setToolbar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity
            ,drawerLayout
            ,R.string.open_drawer
            ,R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        /*
        we used a ? (null operator) because sometimes there will be no toolbar and we need the title to be null
        */
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
