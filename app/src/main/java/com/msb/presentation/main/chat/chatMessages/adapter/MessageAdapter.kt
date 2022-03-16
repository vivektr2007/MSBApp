package com.msb.presentation.main.chat.chatMessages.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msb.R
import com.msb.presentation.utils.MessageClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MessageAdapter(private val userName: String) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var list: ArrayList<MessageClass> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(data: List<MessageClass>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMessage(data: MessageClass) {
        list.add(data)
        notifyItemInserted(list.size)

    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var messageTxt: TextView = itemView.findViewById(R.id.messageTxt)
        private var timeTxt: TextView = itemView.findViewById(R.id.timeTxt)

        fun setData(data: MessageClass) {

            messageTxt.text = data.message

            val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
//            val netDate = Date(data.timeStamp)
//            val date = sdf.format(netDate)
//            binding.timeTxt.text = date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 1)
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.message_left_item_view, parent, false)
            )
        else
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.message_right_item_view, parent, false)
            )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].userName != null && list[position].userName.equals(userName)) {
            return 0
        }
        return 1
    }

    override fun getItemCount(): Int {
        return list.size
    }
}