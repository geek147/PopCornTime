package com.envios.kitabisa.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.R
import com.envios.kitabisa.data.remote.model.Genre
import com.envios.kitabisa.ui.main.*
import com.envios.kitabisa.utils.EndlessRecyclerViewScrollListener
import com.envios.kitabisa.utils.Failed
import com.envios.kitabisa.utils.Loading
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListActivity : AppCompatActivity() {


    val viewModel: MovieListViewModel by viewModel()
    private lateinit var adapter: MovieByGenreAdapter
    private var page = 1
    private  var genreItem: Genre? = Genre(0,"")


    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val intent: Intent = intent
        genreItem  = intent.getParcelableExtra<Genre>("genre")

        supportActionBar?.title = genreItem?.name

            setupRecylerView()
        //Get Now Playing data and movie Genres
        genreItem?.id.toString().let { viewModel.getMoviesByGenre(it, page.toString()) }

        rv_movie_list.addOnScrollListener(scrollListener)

        observeData()
    }



    private fun setupRecylerView() {
        rv_movie_list.setHasFixedSize(true)
        val gridLayoutManager : GridLayoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        rv_movie_list.layoutManager = gridLayoutManager
        adapter = MovieByGenreAdapter(this)
        adapter.setHasStableIds(true)
        rv_movie_list.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView?
            ) {
                val newPage = page + 1
                genreItem?.id.toString().let { viewModel.getMoviesByGenre(it, newPage.toString()) }

            }

        }
    }

    private fun observeData() {
        viewModel.moviesByGenre.observe(this, Observer {
            when (it) {
                is Loading -> {
                    pg_progress_list.visibility = View.VISIBLE
                }

                is MovieListViewModel.moviesLoaded -> {
                    if (!it.movieList.isNullOrEmpty()) {
                        adapter.addData(it.movieList)
                        adapter.notifyDataSetChanged()
                    }
                    pg_progress_list.visibility = View.GONE

                }

                is Failed -> {
                    if (it.error != null) Toast.makeText(this,it.error!!, Toast.LENGTH_LONG).show()
                    pg_progress_list.visibility = View.GONE

                }
            }


        })
    }


}