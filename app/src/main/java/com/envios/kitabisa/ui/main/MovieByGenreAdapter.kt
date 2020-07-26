package com.envios.kitabisa.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.BuildConfig
import com.envios.kitabisa.R
import com.envios.kitabisa.data.remote.model.Movie
import com.envios.kitabisa.databinding.AdapterCarouselBinding
import com.envios.kitabisa.ui.detail.DetailActivity
import com.envios.kitabisa.utils.BindingConverters
import kotlinx.android.synthetic.main.adapter_movie_by_genres.view.*

class MovieByGenreAdapter(private var context: Context) : RecyclerView.Adapter<MovieByGenreViewHolder>() {

    private var items: List<Movie?>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieByGenreViewHolder =
        MovieByGenreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie_by_genres, parent, false))


    override fun onBindViewHolder(holder: MovieByGenreViewHolder, position: Int) {
        items?.get(holder.adapterPosition).let {
            holder.bindData(it, context )
        }
    }

    override fun getItemCount(): Int {
        return if (items.isNullOrEmpty()) {
            0
        } else{
            items!!.size
        }
    }


    fun setItems(newItems: List<Movie?>?) {
        items = newItems
        notifyDataSetChanged()
    }
}

class MovieByGenreViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

    fun bindData(model: Movie?, context: Context){

        BindingConverters.loadImage(view.iv_movie_by_genre_poster, BuildConfig.IMAGE_URL+model?.posterPath)
        itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("movie_id", model?.id.toString())
            context.startActivity(intent)
        }
    }



}