/*
 * Copyright 2017 Martin Chamarro (@martinchamarro)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.martinchamarro.muvis.presentation.views.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.martinchamarro.muvis.R
import com.martinchamarro.muvis.domain.model.Cast
import com.martinchamarro.muvis.domain.model.Detail
import com.martinchamarro.muvis.domain.model.Movie
import com.martinchamarro.muvis.domain.model.Video
import com.martinchamarro.muvis.globalutils.tracker.Tracker
import com.martinchamarro.muvis.globalutils.tracker.event.FavMovie
import com.martinchamarro.muvis.globalutils.tracker.event.ShowMovie
import com.martinchamarro.muvis.globalutils.tracker.event.UnfavMovie
import com.martinchamarro.muvis.presentation.extensions.activityComponent
import com.martinchamarro.muvis.presentation.extensions.fullScreen
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.share
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailContract.View {

    @Inject lateinit var presenter: DetailContract.Presenter
    @Inject lateinit var tracker: Tracker
    private lateinit var renderer: DetailRenderer

    companion object {
        val MOVIE_ID = "movie_id"
        fun createIntent(ctx: Context, movieId: Int): Intent {
            return ctx.intentFor<DetailActivity>(MOVIE_ID to movieId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fullScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        injectDependencies()
        configureToolbar()
        initializeRenderer()
        initializePresenter()
        setListeners()
        trackShowMovie()
    }

    private fun injectDependencies() = activityComponent.inject(this)

    private fun configureToolbar() {
        toolbar.inflateMenu(R.menu.detail_menu)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.setOnMenuItemClickListener { presenter.shareMovie(); true }
        // FIX: there's a bug that cuts the appbar title
        // https://stackoverflow.com/a/43676163/2271287
        // https://issuetracker.google.com/issues/37140811
        collapsingToolbar.post { collapsingToolbar.requestLayout() }
    }

    private fun initializeRenderer() {
        renderer = DetailRenderer(rootView)
    }

    private fun initializePresenter() {
        presenter.view = this
        presenter.initialize()
    }

    private fun setListeners() {
        fabView.onClick { onFabClick() }
        videoThumbnail.onClick { presenter.showTrailer() }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun getMovieId() = intent.getIntExtra(MOVIE_ID, -1)

    override fun render(movie: Movie) = renderer.render(movie)

    override fun render(detail: Detail) = renderer.render(detail)

    override fun render(credits: List<Cast>) = renderer.render(credits)

    override fun render(trailer: Video) = renderer.render(trailer)

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun shareMovie(url: String) { share(url) }

    private fun onFabClick() {
        val id = getMovieId()
        tracker.track(if (presenter.isFavorite) UnfavMovie(id) else FavMovie(id))
        presenter.setFavorite()
    }

    private fun trackShowMovie() = tracker.track(ShowMovie(getMovieId()))

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}
