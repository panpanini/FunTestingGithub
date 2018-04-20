//package jp.co.panpanini.mokumokugithub
//
//import io.reactivex.processors.BehaviorProcessor
//import jp.co.panpanini.mokumokugithub.model.User
//import org.junit.Before
//import org.junit.Test
//
//import org.junit.Assert.*
//
//@Suppress("RemoveRedundantBackticks")
//class GithubRepositoryTest {
//
//    private lateinit var target: GithubRepository
//
//    private val user = User("username", "name", "imageUrl", "location", 0)
//
//    @Before
//    fun setUp() {
//        target = GithubRepository()
//    }
//
//
//    @Test
//    fun setUser() {
//        target.setUser(user)
//            .test()
//            .assertNoErrors()
//        //　テストになってなくない？
//    }
//
//    @Test
//    fun `observeUser should return values from behaviorProcessor`() {
//        val behaviorProcessor = BehaviorProcessor.createDefault(user)
//
//        target = GithubRepository(behaviorProcessor)
//
//        target.observeUser()
//            .test()
//            .assertNoErrors()
//            .assertValue(user)
//
//    }
//
//}