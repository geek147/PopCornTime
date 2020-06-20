package com.envios.kitabisa.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.envios.kitabisa.R

import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var adapter: FavoriteAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fab_menu.visibility = View.GONE

        setupRecyclerView()
        getListMovies()
        observeData()
    }

    private fun setupRecyclerView() {
        rv_movies.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        rv_movies.layoutManager = linearLayoutManager
        adapter = FavoriteAdapter(requireContext())
        adapter.setHasStableIds(true)
        rv_movies.adapter = adapter

    }

    private fun observeData () {
        viewModel.favorites.observe(viewLifecycleOwner, Observer{
            adapter.addData(it)
            adapter.notifyDataSetChanged()

        })
    }

    private fun getListMovies() {
        viewModel.getListFavorites()
        rv_movies.visibility = View.VISIBLE

    }

}
