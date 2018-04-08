package jp.co.panpanini.mokumokugithub

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import jp.co.panpanini.mokumokugithub.model.User

class GithubRepository(private val user: BehaviorProcessor<User> = BehaviorProcessor.create()) {

    fun setUser(user: User) : Completable = Completable.fromAction {
        this.user.onNext(user)
    }

    fun observeUser(): Flowable<User> = user.hide()

}