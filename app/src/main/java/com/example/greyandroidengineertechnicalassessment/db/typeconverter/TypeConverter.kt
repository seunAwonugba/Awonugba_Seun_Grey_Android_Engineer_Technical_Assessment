//package com.example.greyandroidengineertechnicalassessment.db.typeconverter
//
//import androidx.room.TypeConverter
//import com.example.greyandroidengineertechnicalassessment.remote.repository.License
//import com.example.greyandroidengineertechnicalassessment.remote.repository.Owner
//import org.json.JSONObject
//
//class TypeConverter {
//
//    @TypeConverter
//    fun fromLicense(license : License) : String{
//        return JSONObject().apply {
//            put("key", license.key)
//            put("name", license.name)
//            put("node_id", license.node_id)
//            put("spdx_id", license.spdx_id)
//            put("url", license.url)
//        }.toString()
//    }
//
//    @TypeConverter
//    fun toLicense(string: String) : License{
//        val json = JSONObject(string)
//        return License(
//            json.optString("key"),
//            json.optString("name"),
//            json.optString("node_id"),
//            json.optString("spdx_id"),
//            json.optString("url"),
//        )
//    }
//
//    @TypeConverter
//    fun fromOwner(owner : Owner) : String{
//        return JSONObject().apply {
//            put("avatar_url", owner.avatar_url)
//            put("events_url", owner.events_url)
//            put("followers_url", owner.followers_url)
//            put("following_url", owner.following_url)
//            put("gists_url", owner.gists_url)
//            put("gravatar_id", owner.gravatar_id)
//            put("html_url", owner.html_url)
//            put("id", owner.id)
//            put("login", owner.login)
//            put("node_id", owner.node_id)
//            put("organizations_url", owner.organizations_url)
//            put("received_events_url", owner.received_events_url)
//            put("repos_url", owner.repos_url)
//            put("site_admin", owner.site_admin)
//            put("starred_url", owner.starred_url)
//            put("subscriptions_url", owner.subscriptions_url)
//            put("type", owner.type)
//            put("url", owner.url)
//        }.toString()
//    }
//
//
//    @TypeConverter
//    fun toOwner(string: String) : Owner{
//        val json = JSONObject(string)
//        return Owner(
//            json.optString("avatar_url"),
//            json.optString("events_url"),
//            json.optString("followers_url"),
//            json.optString("following_url"),
//            json.optString("gists_url"),
//            json.optString("gravatar_id"),
//            json.optString("html_url"),
//            json.optInt("id"),
//            json.optString("login"),
//            json.optString("node_id"),
//            json.optString("organizations_url"),
//            json.optString("received_events_url"),
//            json.optString("repos_url"),
//            json.optBoolean("site_admin"),
//            json.optString("starred_url"),
//            json.optString("subscriptions_url"),
//            json.optString("type"),
//            json.optString("url"),
//        )
//    }
//
//    @TypeConverter
//    fun fromString(stringListString: String): List<String> {
//        return stringListString.split(",").map { it }
//    }
//
//    @TypeConverter
//    fun toString(stringList: List<String>): String {
//        return stringList.joinToString(separator = ",")
//    }
//
//}
