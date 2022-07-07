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

        var profileImage : ImageView = itemView.findViewById(R.id.profile_image)
        var firstName : TextView = itemView.findViewById(R.id.firstName)
        var lastName : TextView = itemView.findViewById(R.id.lastName)
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
        holder.firstName.text = "${fullName[0]}/"
        holder.lastName.text = fullName[1]
        holder.star.text = gitHubRepoData.star.toString()
        holder.language.text = gitHubRepoData.language
        holder.description.text = gitHubRepoData.description

//        when{
//            gitHubRepoData.topics.isEmpty() -> {
//                holder.topicOne.visibility = View.INVISIBLE
//                holder.topicTwo.visibility = View.INVISIBLE
//                holder.topicTwo.visibility = View.INVISIBLE
//            }
//            gitHubRepoData.topics.size == 1 -> {
//                holder.topicOne.visibility = View.VISIBLE
//                holder.topicTwo.visibility = View.GONE
//                holder.topicThree.visibility = View.GONE
//                holder.topicOne.text = gitHubRepoData.topics[0]
//            }
//            gitHubRepoData.topics.size == 2 -> {
//                holder.topicOne.visibility = View.VISIBLE
//                holder.topicTwo.visibility = View.VISIBLE
//                holder.topicThree.visibility = View.GONE
//                holder.topicOne.text = gitHubRepoData.topics[0]
//                holder.topicTwo.text = gitHubRepoData.topics[1]
//            }
//            gitHubRepoData.topics.size >= 3 -> {
//                holder.topicOne.visibility = View.VISIBLE
//                holder.topicTwo.visibility = View.VISIBLE
//                holder.topicThree.visibility = View.VISIBLE
//                holder.topicOne.text = gitHubRepoData.topics[0]
//                holder.topicTwo.text = gitHubRepoData.topics[1]
//                holder.topicThree.text = gitHubRepoData.topics[2]
//            }
//
//
//        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}