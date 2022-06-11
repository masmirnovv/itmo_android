package com.example.hw_contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactsActivity : AppCompatActivity() {

    lateinit var viewManager: LinearLayoutManager

    lateinit var searchBar: EditText
    lateinit var searchHint: TextView
    lateinit var searchNoContacts: TextView

    lateinit var contactsList: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        viewManager = LinearLayoutManager(this)

        contactsList = ContactFetcher().fetchAllContacts(baseContext)

        Toast.makeText(baseContext, getString(R.string.loaded, contactsList.size), Toast.LENGTH_LONG).show()


        searchHint = findViewById(R.id.search_hint)
        searchNoContacts = findViewById(R.id.search_no_contacts)
        searchBar = findViewById(R.id.search)

        update()

        searchBar.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                update()
            }
            override fun afterTextChanged(s: Editable?) {}

        })

    }



    fun update() {
        val search = searchBar.text.toString()
        val contacts = Contact.search(contactsList, search)

        searchHint.visibility = if (search.isEmpty()) View.VISIBLE else View.INVISIBLE
        searchNoContacts.visibility = if (contacts.isEmpty()) View.VISIBLE else View.INVISIBLE

        recycler_view.apply {
            layoutManager = viewManager
            adapter = ContactAdapter(contacts) {
                val uri = Uri.parse("tel:${it.rawPhoneNumber}")
                val intent = Intent(Intent.ACTION_DIAL, uri)
                startActivity(intent)
            }
        }
    }

}