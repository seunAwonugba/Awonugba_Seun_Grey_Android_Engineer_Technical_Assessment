//package com.example.greyandroidengineertechnicalassessment.db
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.example.greyandroidengineertechnicalassessment.remote.repository.Item
//
//@Dao
//interface GitHubRepoDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun upsert(gitHubRepoItem : Item)
//
//    @Query("SELECT * FROM github_repository")
//    fun getAllGithubRepoData() : LiveData<List<Item>>
//
//    @Delete
//    suspend fun deleteAllGitHubRepository(gitHubItem : Item)
//}