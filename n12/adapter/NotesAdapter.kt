package com.example.n12.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.n12.databinding.ItemUserBinding

import com.example.n12.model.GithubUserDetails

class NotesAdapter(
    context: Context
) : ListAdapter<GithubUserDetails, UserDViewHolder>(DIFF_UTIL) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDViewHolder {
        return UserDViewHolder(
            binding = ItemUserBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserDViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<GithubUserDetails>() {
            override fun areItemsTheSame(
                oldItem: GithubUserDetails,
                newItem: GithubUserDetails
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubUserDetails,
                newItem: GithubUserDetails
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class UserDViewHolder(

    private val binding: ItemUserBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: GithubUserDetails) {
        with(binding) {
            image.load(user.avatarUrl) {
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            count.text = user.login

        }
    }
}




