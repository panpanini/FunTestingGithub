class GithubService(
  private val githubApi: GithubApi,
  private val repository: GithubRepository
) {

    fun fetchUser(username: String): Completable {
        return githubApi.user(username)
            .flatMapCompletable(repository::setUser)
    }

    fun observeUser(): Flowable<User>
        = repository.observeUser()
}
