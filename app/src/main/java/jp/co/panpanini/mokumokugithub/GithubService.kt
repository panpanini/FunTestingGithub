package jp.co.panpanini.mokumokugithub

import io.reactivex.Completable
import io.reactivex.Flowable
import jp.co.panpanini.mokumokugithub.api.GithubApi
import jp.co.panpanini.mokumokugithub.model.User

class GithubService(private val githubApi: GithubApi
                    = GithubRetrofit.retrofit.create(GithubApi::class.java),
                    private val repository: GithubRepository = GithubRepository()

) {

    fun fetchUser(username: String): Completable {
        return githubApi.user(username)
            .flatMapCompletable(repository::setUser)
    }

    fun observeUser(): Flowable<User> = repository.observeUser()
}