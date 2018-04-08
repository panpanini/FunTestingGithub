package jp.co.panpanini.mokumokugithub

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import jp.co.panpanini.mokumokugithub.model.User
import org.junit.Before
import org.junit.Test

@Suppress("RemoveRedundantBackticks")
class GithubRepositoryTestV2 {

    private lateinit var target: GithubRepository

    private val user: User = mock {  }
    private val behaviorProcessor: BehaviorProcessor<User> = mock { }

    @Before
    fun setUp() {
        target = GithubRepository(behaviorProcessor)
    }

    @Test
    fun `setUser should call behaviourProcessor#onNext`() {
        target.setUser(user)
            .test()
            .assertNoErrors()

        verify(behaviorProcessor, times(1)).onNext(user)
    }

    @Test
    fun `observeUser should return values from behaviorProcessor`() {
        whenever(behaviorProcessor.hide()).thenReturn(
            Flowable.just(user)
        )

        target.observeUser()
            .test()
            .assertNoErrors()
            .assertValue(user)
    }
}