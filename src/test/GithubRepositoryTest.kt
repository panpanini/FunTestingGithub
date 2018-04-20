class GithubRepositoryTest {
  private lateinit var target: GithubRepository
  private val user = User()

  @Test
  fun setUser() {
    target.setUser(user)
          .test()
          .assertNoErrors()
  }

  @Test
  fun `observeUser should return values from behaviorProcessor`() {
    val behaviorProcessor
      = BehaviorProcessor.createDefault(user)

    target = GithubRepository(behaviorProcessor)

    target.observeUser()
          .test()
          .assertNoErrors()
          .assertValue(user)
  }
}
