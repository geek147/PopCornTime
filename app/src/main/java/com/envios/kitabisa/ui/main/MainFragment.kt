package com.envios.kitabisa.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.R
import com.envios.kitabisa.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.carousel_layout.*
import kotlinx.android.synthetic.main.genre_layout.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : Fragment(){

    companion object {
        fun newInstance() = MainFragment()
        const val NOW_PLAYING : String = "NOW_PLAYING"
        const val TOP_RATED: String = "TOP_RATED"
        const val POPULAR: String = "POPULAR"
        const val CAROUSEL: String = "CAROUSEL"
    }

   val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: MovieAdapter
    private lateinit var carouselAdapter: CarouselAdapter
    private var page = 1
    private var statusNow = NOW_PLAYING
    var genreName:String? = ""

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        menu_popular.setOnClickListener{
            page = 1
            adapter.setData(emptyList())
            viewModel.getPopularMovie(page.toString())
            statusNow = POPULAR
            rv_movies.visibility = View.VISIBLE
            layout_carousel.visibility = View.GONE
        }

        menu_now_playing.setOnClickListener{
            page = 1
            adapter.setData(emptyList())
            viewModel.getNowPlayingMovie(page.toString())
            statusNow = NOW_PLAYING
            rv_movies.visibility = View.VISIBLE
            layout_carousel.visibility = View.GONE
        }

        menu_top_rated.setOnClickListener{
            page = 1
            adapter.setData(emptyList())
            viewModel.geTopRatedMovie(page.toString())
            statusNow = TOP_RATED
            rv_movies.visibility = View.VISIBLE
            layout_carousel.visibility = View.GONE
        }

        menu_carousel.setOnClickListener{
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



    private fun  setUpRecyclerViewCarousel() {
        carouselAdapter = CarouselAdapter(requireContext())
        rv_carousel.initialize(carouselAdapter)
        rv_carousel.setViewsToChangeColor(listOf(R.id.iv_carousel_poster, R.id.tv_carousel_title, R.id.tv_watch_now))
    }

    private fun setupRecylerView() {
        rv_movies.setHasFixedSize(true)
        val linearLayoutManager  = LinearLayoutManager(context)
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
                val newPage = page +1
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

    private fun observeData () {
        viewModel.movies.observe(viewLifecycleOwner, Observer{
            adapter.addData(it)
            adapter.notifyDataSetChanged()
            carouselAdapter.setItems(it)
        })
    }

    private fun observeGenreData() {
        viewModel.genres.observe(viewLifecycleOwner, Observer{

            if (!it.isNullOrEmpty()) {
                viewModel.getMoviesByGenre(it[0]?.id.toString())
                genreName = it[0]?.name
            }
        })
    }


    private fun  observeMoviesByGenre() {
        viewModel.moviesByGenre.observe(viewLifecycleOwner, Observer{



            val inf = LayoutInflater.from(requireContext())

            if (!it.isNullOrEmpty()) {
                    val view = inf.inflate(R.layout.genre_layout,null)
                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    view.rv_movies_by_genre.layoutManager = layoutManager
                    val adapter = MovieByGenreAdapter(requireContext())
                    view.rv_movies_by_genre.adapter = adapter
                    view.tv_genre.text = genreName
                    adapter.setItems(it)


                    ll_placeholder.addView(view)
                }
        })
    }







}
