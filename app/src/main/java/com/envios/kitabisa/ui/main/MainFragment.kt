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
    }

   val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: MovieAdapter
    private var page = 1
    private var isLoadMore = false


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
            viewModel.getPopularMovie(page.toString())
        }

        menu_now_playing.setOnClickListener{
            page = 1
            viewModel.getNowPlayingMovie(page.toString())
        }

        menu_top_rated.setOnClickListener{
            page = 1
            viewModel.geTopRatedMovie(page.toString())
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
                isLoadMore = true
                val newPage = page +1
                viewModel.getNowPlayingMovie(newPage.toString())

            }

        }
    }

    private fun observeData () {
        viewModel.movies.observe(viewLifecycleOwner, Observer{
            adapter.setData(it)
            adapter.notifyDataSetChanged()

        })
    }

    private fun getListMovies() {

        viewModel.getNowPlayingMovie(page.toString())

    }



}
