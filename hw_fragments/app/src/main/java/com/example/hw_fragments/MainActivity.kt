package com.example.hw_fragments

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var savedStates = SparseArray<Fragment.SavedState>()
    private var currItemId = R.id.navigation_home

    companion object {
        const val SAVED_STATE_CONTAINER_KEY = "container_key"
        const val SAVED_STATE_CURRENT_TAB_KEY = "current_tab_key"
        const val S_HOME = "Home"
        const val S_DASHBOARD = "Dashboard"
        const val S_NOTIFICATIONS = "Notifications"
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> swapFragments(it.itemId, S_HOME)
            R.id.navigation_dashboard -> swapFragments(it.itemId, S_DASHBOARD)
            R.id.navigation_notifications -> swapFragments(it.itemId, S_NOTIFICATIONS)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            savedStates = savedInstanceState.getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY)
            currItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        }
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        navigation.setOnNavigationItemSelectedListener(navListener)
    }



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStates)
        outState?.putInt(SAVED_STATE_CURRENT_TAB_KEY, currItemId)
    }

    private fun swapFragments(actionId: Int, key: String) {
        if (supportFragmentManager.findFragmentByTag(key) == null) {
            saveFragmentState(actionId)
            createFragment(key, actionId)
        }
    }

    private fun createFragment(key: String, actionId: Int) {
        val fragment = ContainerFragment.newInstance(key)
        fragment.setInitialSavedState(savedStates[actionId])
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment, fragment, key)
            .commit()
    }

    private fun saveFragmentState(actionId: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container_fragment)
        if (currentFragment != null) {
            savedStates.put(currItemId, supportFragmentManager.saveFragmentInstanceState(currentFragment))
        }
        currItemId = actionId
    }



    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment != null && fragment.isVisible) {
                with(fragment.childFragmentManager) {
                    if (backStackEntryCount > 0) {
                        popBackStack()
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
