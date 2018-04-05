package jp.co.panpanini.mokumokugithub.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("full_name")
    val name: String,
    val description: String,
    val language: String,
    val fork: Boolean
)