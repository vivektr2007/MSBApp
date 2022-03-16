package com.msb.presentation.main.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.msb.R
import com.msb.databinding.ChatItemViewBinding
import com.msb.framework.GlideApp
import xyz.teknol.core.user.domain.UserListDataItem

class ChatAdapter(private val callback: Callback) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private var list: List<UserListDataItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(data: List<UserListDataItem>) {
        list = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ChatItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: UserListDataItem) {
            GlideApp.with(binding.imageView)
                .load(data.profilePicFullPath)
                .circleCrop()
                .placeholder(R.drawable.ic_place_holder)
                .into(binding.imageView)
            binding.textView17.text = data.name
            binding.textView17.text = data.name
        }

        init {
            binding.root.setOnClickListener {
                callback.onItemClicked(list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.chat_item_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface Callback {
        fun onItemClicked(userListDataItem: UserListDataItem)
    }
}
