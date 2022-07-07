package com.example.greyandroidengineertechnicalassessment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.databinding.UsersListItemBinding
import com.example.greyandroidengineertechnicalassessment.view.data.UsersResponse


class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var profileImage : ImageView = itemView.findViewById(R.id.user_profile_image)
        var login : TextView = itemView.findViewById(R.id.login)
        var loginTwo : TextView = itemView.findViewById(R.id.login_two)
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

    val differ = AsyncListDiffer(this, diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout. users_list_item,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val usersResponse = differ.currentList[position]
        holder.profileImage.load(usersResponse.profilePicture)
        holder.login.text = usersResponse.login
        holder.loginTwo.text = usersResponse.login
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(usersResponse)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((UsersResponse) -> Unit)?  = null

    fun setOnItemClickListener(listener : (UsersResponse) -> Unit){
        onItemClickListener = listener
    }
}