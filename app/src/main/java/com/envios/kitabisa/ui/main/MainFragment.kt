package com.envios.kitabisa.ui.main


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.R
import com.envios.kitabisa.data.remote.model.Genre
import com.envios.kitabisa.ui.list.MovieListActivity
import com.envios.kitabisa.utils.EndlessRecyclerViewScrollListener
import com.envios.kitabisa.utils.Failed
import com.envios.kitabisa.utils.Loading
import kotlinx.android.synthetic.main.carousel_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val NOW_PLAYING: String = "NOW_PLAYING"
        const val TOP_RATED: String = "TOP_RATED"
        const val POPULAR: String = "POPULAR"
        const val CAROUSEL: String = "CAROUSEL"
    }

    val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: MovieAdapter
    private lateinit var carouselAdapter: CarouselAdapter
    private var page = 1
    private var statusNow = CAROUSEL
    private var genreItem: Genre? = Genre(0, "")

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        menu_popular.setOnClickListener {
            page = 1
            adapter.setData(emptyList())
            viewModel.getPopularMovie(page.toString())
            statusNow = POPULAR
            rv_movies.visibility = View.VISIBLE
            layout_carousel.visibility = View.GONE
        }

        menu_now_playing.setOnClickListener {
            page = 1
            adapter.setData(emptyList())
            viewModel.getNowPlayingMovie(page.toString())
            statusNow = NOW_PLAYING
            rv_movies.visibility = View.VISIBLE
            layout_carousel.visibility = View.GONE
        }

        menu_top_rated.setOnClickListener {
            page = 1
            adapter.setData(emptyList())
            viewModel.geTopRatedMovie(page.toString())
            statusNow = TOP_RATED
            rv_movies.visibility = View.VISIBLE
            layout_carousel.visibility = View.GONE
        }

        menu_carousel.setOnClickListener {
            page = 1
            adapter.setData(emptyList())
            viewModel.getNowPlayingMovie(page.toString())
            viewModel.getMovieGenres()
            statusNow = CAROUSEL
            rv_movies.visibility = View.GONE
            layout_carousel.visibility = View.VISIBLE
        }

        setupRecylerView()
        setUpRecyclerViewCarousel()
        //Get Now Playing data and movie Genres
        viewModel.getNowPlayingMovie(page.toString())
        viewModel.getMovieGenres()

        rv_movies.addOnScrollListener(scrollListener)

        observeData()
        observeGenreData()
        observeMoviesByGenre()
    }


    private fun setUpRecyclerViewCarousel() {
        carouselAdapter = CarouselAdapter(requireContext())
        rv_carousel.initialize(carouselAdapter)
        rv_carousel.setViewsToChangeColor(
            listOf(
                R.id.iv_carousel_poster,
                R.id.tv_carousel_title,
                R.id.tv_watch_now
            )
        )
    }

    private fun setupRecylerView() {
        rv_movies.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        rv_movies.layoutManager = linearLayoutManager
        adapter = MovieAdapter(requireContext())
        adapter.setHasStableIds(true)
        rv_movies.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView?
            ) {
                val newPage = page + 1
                if (statusNow == NOW_PLAYING) {
                    viewModel.getNowPlayingMovie(newPage.toString())
                } else if (statusNow == POPULAR) {
                    viewModel.getPopularMovie(newPage.toString())
                } else if (statusNow == TOP_RATED) {
                    viewModel.geTopRatedMovie(newPage.toString())
                }

            }

        }
    }

    private fun observeData() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> {
                    pg_progress_carousel.visibility = View.VISIBLE
                    pg_progress_now_playing.visibility = View.VISIBLE
                }

                is MainViewModel.moviesLoaded -> {
                    if (!it.movieList.isNullOrEmpty()) {
                        adapter.addData(it.movieList)
                        adapter.notifyDataSetChanged()
                        if (statusNow == CAROUSEL) {
                            carouselAdapter.setItems(it.movieList)
                        }

                        val layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        rv_now_playing.layoutManager = layoutManager
                        val adapter = MovieByGenreAdapter(requireContext())
                        rv_now_playing.adapter = adapter
                        tv_now_playing.text = "Now Playing"
                        adapter.setData(it.movieList)
                        adapter.notifyDataSetChanged()
                    }

                    pg_progress_carousel.visibility = View.GONE
                    pg_progress_now_playing.visibility = View.GONE

                }

                is Failed -> {
                    if (it.error != null) Toast.makeText(
                        requireContext(),
                        it.error!!,
                        Toast.LENGTH_LONG
                    ).show()
                    pg_progress_carousel.visibility = View.GONE
                    pg_progress_now_playing.visibility = View.GONE

                }
            }


        })
    }

    private fun observeGenreData() {
        viewModel.genres.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> {

                }

                is MainViewModel.genreLoaded -> {
                    if (!it.genreList.isNullOrEmpty()) {
                        viewModel.getMoviesByGenre(it.genreList[0]?.id.toString(), "1")
                        genreItem = it.genreList[0]
                    }
                }

                is Failed -> {
                    if (it.error != null) Toast.makeText(
                        requireContext(),
                        it.error!!,
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        })
    }


    private fun observeMoviesByGenre() {
        viewModel.moviesByGenre.observe(viewLifecycleOwner, Observer {

            when (it) {

                is Loading -> {
                    pg_progress_genre.visibility = View.VISIBLE
                }

                is MainViewModel.moviesLoaded -> {

                    if (!it.movieList.isNullOrEmpty()) {
                        val layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        rv_movies_by_genre.layoutManager = layoutManager
                        val adapter = MovieByGenreAdapter(requireContext())
                        rv_movies_by_genre.adapter = adapter
                        tv_genre.text = genreItem?.name
                        adapter.setData(it.movieList)
                        adapter.notifyDataSetChanged()
                        tv_see_all.text = "See All"
                        tv_see_all.setOnClickListener {
                            val intent = Intent(context, MovieListActivity::class.java)
                            intent.putExtra("genre", genreItem)
                            startActivity(intent)
                        }

                    }

                    pg_progress_genre.visibility = View.GONE
                }

                is Failed -> {
                    pg_progress_genre.visibility = View.GONE

                    if (it.error != null) Toast.makeText(
                        requireContext(),
                        it.error!!,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


        })
    }


}
