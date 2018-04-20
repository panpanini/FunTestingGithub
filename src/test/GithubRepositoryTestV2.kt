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
      whenever(behaviorProcessor.hide())
        .thenReturn( Flowable.just(user) )

      target.observeUser()
            .test()
            .assertNoErrors()
            .assertValue(user)
  }
}
