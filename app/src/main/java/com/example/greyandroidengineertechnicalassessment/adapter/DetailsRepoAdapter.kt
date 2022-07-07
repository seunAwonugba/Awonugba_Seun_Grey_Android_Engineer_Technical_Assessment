package com.example.greyandroidengineertechnicalassessment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.view.data.DetailsRepoResponse

class DetailsRepoAdapter : RecyclerView.Adapter<DetailsRepoAdapter.DetailsRepoViewHolder>() {
    inner class DetailsRepoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var firstName : TextView = itemView.findViewById(R.id.details_first_name)
        var lastName : TextView = itemView.findViewById(R.id.lastName)
        var forkedFrom : TextView = itemView.findViewById(R.id.forkedFrom)
        var updateTime : TextView = itemView.findViewById(R.id.update_time)
        var description : TextView = itemView.findViewById(R.id.repo_description)
    }

    private val differCallBack = object : DiffUtil.ItemCallback<DetailsRepoResponse>(){
        override fun areItemsTheSame(
            oldItem: DetailsRepoResponse,
            newItem: DetailsRepoResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DetailsRepoResponse,
            newItem: DetailsRepoResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsRepoViewHolder {
        return DetailsRepoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.users_repo_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailsRepoViewHolder, position: Int) {
        val usersRepo = differ.currentList[position]
        val fullName = usersRepo.fullName.split("/")
        holder.firstName.text = "${fullName[0]}/"
        holder.lastName.text = fullName[0]
        holder.description.text = usersRepo.description
        holder.forkedFrom.text = usersRepo.forkedFrom
        holder.updateTime.text = usersRepo.update
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}