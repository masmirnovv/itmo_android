package com.example.hw_contacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class ContactViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val nameText = root.contact_name!!
    val phoneNumberText = root.contact_phoneNumber!!
}
