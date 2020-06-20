package com.envios.kitabisa.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.R
import com.envios.kitabisa.data.remote.model.Movie
import com.envios.kitabisa.databinding.AdapterMoviesBinding
import com.envios.kitabisa.ui.detail.DetailActivity


class MovieAdapter(private var context: Context): RecyclerView.Adapter<MovieAdapter.MainViewHolder>(){
    private var listMovies: MutableList<Movie?>? = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MainViewHolder{
        val binding: AdapterMoviesBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_movies, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (listMovies.isNullOrEmpty()) {
            0
        } else{
            listMovies!!.size
        }
    }

    override fun getItemId(position: Int): Long {
        val movie: Movie? = listMovies?.get(position)
        return movie!!.id!!.toLong()
    }

    fun addData(list: List<Movie?>?){
        if (list != null) {
            this.listMovies?.addAll(list)
        }
    }

    fun setData(list: List<Movie?>?){
        if (list != null) {
            this.listMovies?.clear()
            this.listMovies?.addAll(list)
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        listMovies?.get(holder.adapterPosition)?.let {
            holder.bindData(it, context )
        }
    }



    class MainViewHolder(private val binding: AdapterMoviesBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindData(model: Movie, context: Context){
            val viewModel = MovieItemViewModel(model)
            binding.itemMovies = viewModel
            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("movie_id", model.id.toString())
                context.startActivity(intent)
            }
        }



    }


}