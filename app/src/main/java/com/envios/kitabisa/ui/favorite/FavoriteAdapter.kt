package com.envios.kitabisa.ui.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.R
import com.envios.kitabisa.data.local.model.Favorite
import com.envios.kitabisa.databinding.AdapterFavoritesBinding
import com.envios.kitabisa.ui.detail.DetailActivity


class FavoriteAdapter(context: Context): RecyclerView.Adapter<FavoriteAdapter.MainViewHolder>(){
    private var listMovies: MutableList<Favorite?>? = mutableListOf()
    private var context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MainViewHolder{
        val binding: AdapterFavoritesBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_favorites, parent, false)
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
        val movie: Favorite? = listMovies?.get(position)
        return movie!!.id!!.toLong()
    }

    fun addData(list: List<Favorite?>?){
        if (list != null) {
            this.listMovies?.addAll(list)
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        listMovies?.get(holder.adapterPosition)?.let {
            holder.bindData(it, context )
        }
    }



    class MainViewHolder(private val binding: AdapterFavoritesBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindData(model: Favorite, context: Context){
            val viewModel = FavoriteItemViewModel(model)
            binding.itemFavorite = viewModel
           
        }



    }


}