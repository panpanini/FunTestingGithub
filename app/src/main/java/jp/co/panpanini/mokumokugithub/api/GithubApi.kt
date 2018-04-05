package jp.co.panpanini.mokumokugithub.api

import io.reactivex.Maybe
import jp.co.panpanini.mokumokugithub.model.Repo
import jp.co.panpanini.mokumokugithub.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{user}")
    fun user(@Path("user") username: String): Maybe<User>

    @GET("users/{user}/repos")
    fun repo(@Path("user") username: String): Maybe<List<Repo>>


}