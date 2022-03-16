package com.msb.presentation.main.user_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msb.R
import com.msb.framework.GlideApp
import contacts.core.Query

class PhoneBookAdapter(private val callback: Callback, private val contacts: Query.Result) :
    RecyclerView.Adapter<PhoneBookAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                callback.onItemClicked()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.phone_book_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(holder.itemView)
            .load(contacts[position].photoUri)
            .placeholder(R.drawable.ic_place_holder)
            .circleCrop()
            .into(holder.itemView.findViewById(R.id.imageView))
        holder.itemView.findViewById<TextView>(R.id.textView17).text = contacts[position].displayNamePrimary
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    interface Callback {
        fun onItemClicked()
    }
}
