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
import com.example.greyandroidengineertechnicalassessment.view.data.RepositoryResponse

class GitHubRepoAdapter : RecyclerView.Adapter<GitHubRepoAdapter.GitHubRepoViewHolder>() {

    inner class GitHubRepoViewHolder(
        itemView : View
    ) : RecyclerView.ViewHolder(itemView){

        var profileImage : ImageView = itemView.findViewById(R.id.details_profile_image)
        var star : TextView = itemView.findViewById(R.id.star)
        var language : TextView = itemView.findViewById(R.id.language)
        var description : TextView = itemView.findViewById(R.id.description)

    }

    private val differCallBack = object : DiffUtil.ItemCallback<RepositoryResponse>(){
        override fun areItemsTheSame(
            oldItem: RepositoryResponse,
            newItem: RepositoryResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RepositoryResponse,
            newItem: RepositoryResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepoViewHolder {
        return GitHubRepoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.repo_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GitHubRepoViewHolder, position: Int) {
        val gitHubRepoData = differ.currentList[position]
        val fullName = gitHubRepoData.fullName.split("/")
        holder.profileImage.load(gitHubRepoData.profilePicture)
        holder.star.text = gitHubRepoData.star.toString()
        holder.language.text = gitHubRepoData.language
        holder.description.text = gitHubRepoData.description
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}