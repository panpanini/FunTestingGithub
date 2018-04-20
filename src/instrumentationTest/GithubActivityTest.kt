@RunWith(AndroidJUnit4::class)
class GithubActivityTest {
    private lateinit var target: GithubActivity

    private var viewModel: GithubViewModel = mock {
        whenever(it.observeName()).thenReturn(Flowable.empty())
        whenever(it.observeLocation()).thenReturn(Flowable.empty())
        whenever(it.observeProfileImageUrl()).thenReturn(Flowable.empty())
        whenever(it.observeRepoCount()).thenReturn(Flowable.empty())

        whenever(it.fetchUser()).thenReturn(Completable.complete())
        whenever(it.setUsername(any())).thenReturn(Completable.complete())
    }


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val context = InstrumentationRegistry.getTargetContext()
        val intent = Intent(context, GithubActivity::class.java)

        target= InstrumentationRegistry
                .getInstrumentation()
                .startActivitySync(intent) as GithubActivity

        target.viewModel = viewModel
    }

    @Test
    fun when_ViewModel_emits_name_it_should_be_set_to_name_TextView() {
        val expected = "name"
        whenever(viewModel.observeName()).thenReturn(
            Flowable.just(expected)
        )

        target.bindData()

        // assert name text is set to name view
        onView(withId(R.id.name))
            .check(matches(withText(expected)))
    }

    @Test
    fun when_ViewModel_emits_location_it_should_be_set_to_location_TextView() {
        val expected = "location"
        whenever(viewModel.observeLocation()).thenReturn(
            Flowable.just(expected)
        )

        target.bindData()

        onView(withId(R.id.location))
            .check(matches(withText(expected)))
    }

    @Test
    fun when_ViewModel_emits_repoCount_it_should_be_set_to_repo_button() {
        val expected = 5
        whenever(viewModel.observeRepoCount()).thenReturn(
            Flowable.just(expected)
        )

        target.bindData()

        onView(withId(R.id.repos_button))
            .check(matches(withText(containsString(expected.toString()))))
    }

    @Test
    fun when_ViewModel_emits_imageUrl_it_should_be_set_to_profile_ImageView() {
        val expected = "imageUrl"
        whenever(viewModel.observeProfileImageUrl()).thenReturn(
            Flowable.just(expected)
        )

        val picasso: Picasso = mock {
            whenever(it.load(any<String>())).thenReturn(mock{})
        }

        Picasso.setSingletonInstance(picasso)

        target.bindData()

        verify(picasso, times(1)).load(expected)
    }

    @Test
    fun when_click_search_button_expect_fetchUser_called() {
        onView(withId(R.id.search_button))
            .perform(click())

        verify(viewModel, times(1)).fetchUser()
    }

    @Test
    fun when_enter_text_into_username_expect_setUsername_called() {
        val expected = "username"

        target.bindData()

        onView(withId(R.id.username_input))
            .perform(typeText(expected))

        verify(viewModel, times(1)).setUsername(expected)
    }

}
