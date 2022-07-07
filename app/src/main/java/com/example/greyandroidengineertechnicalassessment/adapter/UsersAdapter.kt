package com.example.greyandroidengineertechnicalassessment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.greyandroidengineertechnicalassessment.databinding.UsersListItemBinding
import com.example.greyandroidengineertechnicalassessment.view.data.UsersResponse


class UsersAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    inner class MyViewHolder(
        val binding : UsersListItemBinding,
        onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.card.setOnClickListener {
                onItemClickListener.onNavToUserDetails(adapterPosition)
            }
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<UsersResponse>(){

        override fun areItemsTheSame(
            oldItem: UsersResponse,
            newItem: UsersResponse
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: UsersResponse,
            newItem: UsersResponse
        ): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var usersResponse : List<UsersResponse>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            UsersListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = usersResponse[position]

        holder.binding.apply {
            userProfileImage.load(currentUser.profilePicture)
            login.text = currentUser.login
            loginTwo.text = currentUser.login

        }
    }

    override fun getItemCount(): Int {
        return usersResponse.size
    }

    interface OnItemClickListener{
        fun onNavToUserDetails(position: Int)
    }
}