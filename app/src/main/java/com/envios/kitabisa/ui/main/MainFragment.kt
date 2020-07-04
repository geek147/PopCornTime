package com.envios.kitabisa.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.R
import com.envios.kitabisa.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : Fragment(){

    companion object {
        fun newInstance() = MainFragment()
        const val NOW_PLAYING : String = "NOW_PLAYING"
        const val TOP_RATED: String = "TOP_RATED"
        const val POPULAR: String = "POPULAR"
    }

   val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: MovieAdapter
    private var page = 1
    private var statusNow = NOW_PLAYING



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
        }

        menu_now_playing.setOnClickListener{
            page = 1
            adapter.setData(emptyList())
            viewModel.getNowPlayingMovie(page.toString())
            statusNow = NOW_PLAYING

        }

        menu_top_rated.setOnClickListener{
            page = 1
            adapter.setData(emptyList())
            viewModel.geTopRatedMovie(page.toString())
            statusNow = TOP_RATED
        }

        setupRecylerView()
        getListMovies()

        rv_movies.addOnScrollListener(scrollListener)

        observeData()

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

        })
    }

    private fun getListMovies() {

        viewModel.getNowPlayingMovie(page.toString())

    }



}
