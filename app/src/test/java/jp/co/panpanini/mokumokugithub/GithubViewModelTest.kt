//package jp.co.panpanini.mokumokugithub
//
//import com.nhaarman.mockito_kotlin.*
//import io.reactivex.Completable
//import io.reactivex.Flowable
//import io.reactivex.processors.BehaviorProcessor
//import jp.co.panpanini.mokumokugithub.model.User
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//
//import org.junit.Assert.*
//
//@Suppress("RemoveRedundantBackticks")
//class GithubViewModelTest {
//
//    private lateinit var target: GithubViewModel
//
//    private val user: User = mock {  }
//    private val behaviorProcessor: BehaviorProcessor<String> = mock { }
//    private val githubService: GithubService = mock {
//        whenever(it.observeUser()).thenReturn(
//            Flowable.just(user)
//        )
//    }
//
//    @Before
//    fun setUp() {
//        target = GithubViewModel(githubService, behaviorProcessor)
//    }
//
//    @Test
//    fun `observeName should return User#name`() {
//        val expected = "name"
//        whenever(user.name).thenReturn(expected)
//
//        target.observeName()
//            .test()
//            .assertNoErrors()
//            .assertValue(expected)
//
//    }
//
//    @Test
//    fun `observeLocation should return User#location`() {
//        val expected = "location"
//        whenever(user.location).thenReturn(expected)
//
//        target.observeLocation()
//            .test()
//            .assertNoErrors()
//            .assertValue(expected)
//    }
//
//    @Test
//    fun `observeRepoCount should return User#numberOfRepos`() {
//        val expected = 0
//        whenever(user.numberOfRepos).thenReturn(expected)
//
//        target.observeRepoCount()
//            .test()
//            .assertNoErrors()
//            .assertValue(expected)
//    }
//
//    @Test
//    fun `observeRepoCountVisibility should return false when repo count is 0`() {
//        val expected = false
//        whenever(user.numberOfRepos).thenReturn(0)
//
//        target.observeRepoCountVisibility()
//            .test()
//            .assertNoErrors()
//            .assertValue(expected)
//    }
//
//    @Test
//    fun `observeRepoCountVisibility should return true when repo count is positive`() {
//        val expected = true
//        whenever(user.numberOfRepos).thenReturn(1)
//
//        target.observeRepoCountVisibility()
//            .test()
//            .assertNoErrors()
//            .assertValue(expected)
//    }
//
//    @Test
//    fun `observeProfileImageUrl should return User#imageUrl`() {
//        val expected = "profileUrl"
//        whenever(user.imageUrl).thenReturn(expected)
//
//        target.observeProfileImageUrl()
//            .test()
//            .assertNoErrors()
//            .assertValue(expected)
//    }
//
//    @Test
//    fun `setUsername should call behaviorProcessor#onNext`() {
//        val expected = "username"
//
//        target.setUsername(expected)
//            .test()
//            .assertNoErrors()
//            .assertComplete()
//
//        verify(behaviorProcessor, times(1)).onNext(expected)
//
//    }
//
//    @Test
//    fun `fetchUser should call githubService#fetchUser with value from behaviorProcessor`() {
//        val expected = "username"
//        whenever(githubService.fetchUser(any())).thenReturn(Completable.complete())
//        whenever(behaviorProcessor.value).thenReturn(expected)
//
//        target.fetchUser()
//            .test()
//            .assertNoErrors()
//            .assertComplete()
//
//        verify(behaviorProcessor, times(1)).value
//        verify(githubService, times(1)).fetchUser(expected)
//    }
//
//}