package com.example.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bookhub.*
import com.example.bookhub.fragment.AboutAppFragment
import com.example.bookhub.fragment.DashboardFragment
import com.example.bookhub.fragment.FavoritesFragment
import com.example.bookhub.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var toolbar : Toolbar
    lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var frameLayout: FrameLayout
    private lateinit var navigationView : NavigationView
    var prevMenuItem : MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        setToolbar()

        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity
            ,drawerLayout
            , R.string.open_drawer
            , R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (prevMenuItem != null){
                prevMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            prevMenuItem = it

            when(it.itemId){
                R.id.dashboard -> {
                    //Toast.makeText(this@MainActivity,"clicked on dashboard",Toast.LENGTH_SHORT).show()
                    openDashboard()
                    drawerLayout.closeDrawers()
                }
                R.id.favorites -> {
                    //Toast.makeText(this@MainActivity,"clicked on favorites",Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        FavoritesFragment()
                    ).commit()
                    supportActionBar?.title = "Favorites"
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    //Toast.makeText(this@MainActivity,"clicked on profile",Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        ProfileFragment()
                    ).commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.aboutApp -> {
                    //Toast.makeText(this@MainActivity,"clicked on About App",Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        AboutAppFragment()
                    ).commit()
                    supportActionBar?.title = "About App"
                    drawerLayout.closeDrawers()
                }
            }

            return@setNavigationItemSelectedListener true
        }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openDashboard(){
        supportFragmentManager.beginTransaction().replace(
            R.id.frame,
            DashboardFragment()
        ).addToBackStack("dashboard").commit()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }

    }

}
