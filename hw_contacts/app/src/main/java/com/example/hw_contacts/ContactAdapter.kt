package com.example.hw_contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter (val contacts: List<Contact>, val onClick: (Contact) -> Unit) :
RecyclerView.Adapter<ContactViewHolder>() {

    override fun onBindViewHolder(holder: ContactViewHolder, pos: Int) {
        holder.nameText.text = contacts[pos].name
        holder.phoneNumberText.text = contacts[pos].phoneNumber
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val holder = ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item, parent, false
            )
        )
        holder.root.setOnClickListener {
            onClick(contacts[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount(): Int = contacts.size

}