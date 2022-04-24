package com.example.n12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.n12.model.GithubUser
import com.example.n12.databinding.ItemUserBinding
import com.example.n12.databinding.ItemLoadingBinding

class CountAdapter(
    private val items: List<GithubUser>,
    private val itemClick: (GithubUser) -> Unit
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(
            binding = ItemUserBinding.inflate(layoutInflater, parent, false),
            onUserClicked = itemClick
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val onUserClicked: (GithubUser) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(githubUser: GithubUser) {
        with(binding) {
            binding.count.text = githubUser.login
            image.load(githubUser.avatarUrl) {
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            binding.root.setOnClickListener {
                onUserClicked(githubUser)
            }
        }
    }
}

class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)