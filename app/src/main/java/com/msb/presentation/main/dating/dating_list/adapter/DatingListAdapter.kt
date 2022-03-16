package com.msb.presentation.main.dating.dating_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.msb.R
import com.msb.databinding.DatingItemBinding
import com.msb.framework.GlideApp

class DatingListAdapter(
    private var list: List<String> = emptyList()
) : RecyclerView.Adapter<DatingListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.dating_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = 50

    class ViewHolder(private val binding: DatingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData() {
            GlideApp
                .with(binding.itemImage)
                .load("https://source.unsplash.com/THozNzxEP3g/600x800")
                .into(binding.itemImage)
        }

    }

}
