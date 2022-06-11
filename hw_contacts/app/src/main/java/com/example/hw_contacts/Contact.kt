package com.example.hw_contacts

import android.content.Context
import android.provider.ContactsContract
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

data class Contact(val name: String, val phoneNumber: String, val rawPhoneNumber: String) {

    constructor(name: String, phoneNumber: String) :
            this(name, phoneNumber, rawPhoneNumber(phoneNumber))

    companion object {

        private fun rawPhoneNumber(phoneNumber : String) : String {
            val res = StringBuilder()
            for (c in phoneNumber)
                if (c in '0'..'9')
                    res.append(c)
            return res.toString()
        }

        private fun String.low() = this.toLowerCase(Locale.getDefault())

        fun search(contacts: List<Contact>, str: String): ArrayList<Contact> {
            val resContacts = ArrayList<Contact>()
            for (ct in contacts)
                if (ct.name.low().contains(str.low()) || ct.phoneNumber.contains(str) || ct.rawPhoneNumber.contains(str))
                    resContacts.add(ct)
            return resContacts
        }

    }

}