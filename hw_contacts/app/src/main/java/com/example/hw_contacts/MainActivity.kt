package com.example.hw_contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permsButton: Button = findViewById(R.id.pls_allow_button)
        permsButton.setOnClickListener {
            requestPermissions()
        }

        if (permissionsAlreadyGranted())
            openContactsList()
        else
            requestPermissions()
    }



    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, ALL_REQUIRED_PERMISSIONS, CONTACTS_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == CONTACTS_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openContactsList()
            }
        }
    }

    private fun permissionsAlreadyGranted() = ALL_REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun openContactsList() {
        val intent = Intent(this, ContactsActivity::class.java)
        startActivity(intent)
    }



    companion object {
        private const val CONTACTS_REQUEST = 1
        private val ALL_REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_CONTACTS)
    }

}