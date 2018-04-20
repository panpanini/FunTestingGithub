class GithubRepository(
  private val user: BehaviorProcessor<User>
) {

    fun setUser(user: User) : Completable
      = Completable.fromAction {
        this.user.onNext(user)
      }

    fun observeUser(): Flowable<User> = user.hide()

}
