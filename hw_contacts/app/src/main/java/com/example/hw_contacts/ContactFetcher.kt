package com.example.hw_contacts

import android.content.Context
import android.provider.ContactsContract

class ContactFetcher {

    fun fetchAllContacts(context: Context): ArrayList<Contact> {
        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
            .use { cursor ->
                if (cursor == null) return ArrayList()
                val builder = ArrayList<Contact>()
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) ?: "N/A"
                    val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ?: "N/A"
                    builder.add(Contact(name, phoneNumber))
                }
//                    for (i in 0..99)
//                        builder.add(Contact("Фейк", "+7(999)999-99-$i"))
                return builder
            }
    }

}