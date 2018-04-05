package jp.co.panpanini.mokumokugithub

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor


class GithubViewModel(private val githubService: GithubService = GithubService()) {

    private val username: BehaviorProcessor<String> = BehaviorProcessor.createDefault("")

    fun observeName(): Flowable<String> = githubService.observeUser().map { it.name }

    fun observeLocation(): Flowable<String> = githubService.observeUser().map { it.location }

    fun observeRepoCount(): Flowable<Int> = githubService.observeUser().map { it.numberOfRepos }

    fun observeRepoCountVisibility(): Flowable<Boolean> = observeRepoCount()
        .defaultIfEmpty(0)
        .map { it > 0 }

    fun observeProfileImageUrl(): Flowable<String> = githubService.observeUser().map { it.imageUrl }



    fun setUsername(username: String) = Completable.fromAction {
        this.username.onNext(username)
    }

    fun fetchUser(): Completable = githubService.fetchUser(username.value)


}