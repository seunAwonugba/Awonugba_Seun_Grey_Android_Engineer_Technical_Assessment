//package com.example.greyandroidengineertechnicalassessment.db
//
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.example.greyandroidengineertechnicalassessment.db.typeconverter.TypeConverter
//import com.example.greyandroidengineertechnicalassessment.remote.repository.Item
//
//@Database(
//    entities = [Item::class],
//    version = 1
//)
//@TypeConverters(TypeConverter::class)
//abstract class GitHubRepoDataBase : RoomDatabase() {
//    abstract fun getGitHubRepoDao() : GitHubRepoDao
//}