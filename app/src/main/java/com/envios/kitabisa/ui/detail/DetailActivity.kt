package com.envios.kitabisa.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.envios.kitabisa.BuildConfig
import com.envios.kitabisa.R
import com.envios.kitabisa.data.remote.model.MovieDetail


import kotlinx.android.synthetic.main.detail_activity.*
import org.koin.android.viewmodel.ext.android.viewModel


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    lateinit var adapter: ReviewAdapter
    private var movieDetail: MovieDetail? = null
    private lateinit var movieId : String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val intent: Intent = intent
        movieId  = intent.getStringExtra("movie_id")

        tv_review.visibility = View.VISIBLE
        setupRecylerView()
        detailViewModel.getReviews(movieId)
        detailViewModel.getMovieDetail(movieId)
        detailViewModel.checkIsFavoriteMovie(movieId.toInt())
        observeReviewData()
        observeMovieDetail()
        observeIsFavorite()

        tg_favorite.isChecked = false

        tg_favorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tg_favorite.setBackgroundResource(R.drawable.star_yellow)
                detailViewModel.insertMovieToFavorite(movieDetail)
            } else {
                tg_favorite.setBackgroundResource(R.drawable.star_grey)
                detailViewModel.deleteMovieFromFavorite(movieId.toInt())
            }
        }


    }

    private fun observeIsFavorite() {
        detailViewModel.isFavorite.observe(this, Observer {

            if(it != null) {
                tg_favorite.setBackgroundResource(R.drawable.star_yellow)

            }else {
                tg_favorite.setBackgroundResource(R.drawable.star_grey)

            }
        })
    }

    private fun setupRecylerView() {
        rv_review.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = ReviewAdapter(this)
        rv_review.adapter = adapter

    }

    private fun observeReviewData() {
        detailViewModel.reviews.observe(this, Observer {
            adapter.addData(it)
            adapter.notifyDataSetChanged()

        })
    }

    private fun observeMovieDetail() {
        detailViewModel.movieDetail.observe(this, Observer {
            movieDetail = it

            tv_title.text = it?.title
            supportActionBar?.title = it?.title

            tv_release_date.text = "Release date : " +it?.releaseDate
            tv_overview.text = "Deskripsi:\n" + it?.overview
            val url:String = BuildConfig.IMAGE_URL+ it?.posterPath
            Glide.with(this)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))
                .into(iv_poster)

            val urlBackDrop:String = BuildConfig.BACKDROP_URL+ it?.backdropPath
            Glide.with(this)
                .load(urlBackDrop)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))
                .into(iv_backdrop)

        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }





}