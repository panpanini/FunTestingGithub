package jp.co.panpanini.mokumokugithub

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.jakewharton.rxbinding2.widget.RxTextView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

@SuppressLint("VisibleForTests")
class GithubActivity : AppCompatActivity() {

    var viewModel = GithubViewModel()

    @BindView(R.id.image)
    lateinit var profileImage: ImageView

    @BindView(R.id.username_input)
    lateinit var usernameInput: EditText

    @BindView(R.id.name)
    lateinit var name: TextView

    @BindView(R.id.location)
    lateinit var location: TextView

    @BindView(R.id.repos_button)
    lateinit var repos: Button

    private val disposables =  CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        Timber.plant(Timber.DebugTree())

        bindData()
    }

    override fun onResume() {
        super.onResume()
        bindVisibility()
    }

    private fun bindVisibility() {
        disposables.addAll(
            viewModel.observeRepoCountVisibility()
                .defaultIfEmpty(false)
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    if (it) View.VISIBLE else View.GONE
                }
                .subscribe(repos::setVisibility)
        )
    }

    @VisibleForTesting
    fun bindData() {
        disposables.addAll(
            viewModel.observeName()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(name::setText),

            viewModel.observeLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location::setText),

            viewModel.observeProfileImageUrl()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    profileImage.run {
                        Picasso.with(context)
                            .load(it)
                            .into(this)
                    }
                },

            viewModel.observeRepoCount()
                .observeOn(AndroidSchedulers.mainThread())
                .map { getString(R.string.number_of_repos, it) }
                .subscribe (repos::setText),

            RxTextView.textChanges(usernameInput)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .flatMapCompletable(viewModel::setUsername)
                .subscribe()

        )
    }

    @OnClick(R.id.search_button)
    fun onClickSearch() {
        disposables.add(
            viewModel
                .fetchUser()
                .subscribeOn(Schedulers.io())
                .subscribe({}, Timber::e)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

}
