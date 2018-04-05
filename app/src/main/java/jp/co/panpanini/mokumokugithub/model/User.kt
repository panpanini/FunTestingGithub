package jp.co.panpanini.mokumokugithub.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val username: String,
    val name: String,
    @SerializedName("avatar_url")
    val imageUrl: String,
    val location: String,
    @SerializedName("public_repos")
    val numberOfRepos: Int
)