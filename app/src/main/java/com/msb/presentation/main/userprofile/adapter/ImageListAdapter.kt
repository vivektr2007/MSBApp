package com.msb.presentation.main.userprofile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.msb.R
import com.msb.databinding.AnonymousImageListItemBinding
import com.msb.framework.GlideApp
import xyz.teknol.core.auth.domain.GetAnonymousProfileData
import xyz.teknol.utils.extensions.doGone
import xyz.teknol.utils.extensions.doVisible

class ImageListAdapter : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    private val list: ArrayList<GetAnonymousProfileData.GetAnonymousProfileDataItem> = ArrayList()
    private var selectedPic: String = ""

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<GetAnonymousProfileData.GetAnonymousProfileDataItem>?) {
        this.list.clear()
        this.list.addAll(list!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.anonymous_image_list_item,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedItem(selectedPic: String) {
        this.selectedPic = selectedPic
        notifyDataSetChanged()
    }

    fun getSelectedItem(): String {
        return selectedPic
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(private val binding: AnonymousImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                list[adapterPosition].profilePic?.let { selectedPic = it }
                notifyDataSetChanged()
            }
        }

        fun setData(data: GetAnonymousProfileData.GetAnonymousProfileDataItem) {
            if (data.profilePic == selectedPic) {
                binding.imageView.doVisible()
            } else {
                binding.imageView.doGone()
            }
            GlideApp.with(binding.imageView2).load(data.profilePicFullPath).into(binding.imageView2)
        }
    }

}

