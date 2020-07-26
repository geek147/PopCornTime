package com.envios.kitabisa.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.R
import com.envios.kitabisa.data.remote.model.Movie
import com.envios.kitabisa.databinding.AdapterCarouselBinding
import com.envios.kitabisa.ui.detail.DetailActivity

class CarouselAdapter(private var context: Context) : RecyclerView.Adapter<CarouselViewHolder>() {

    private var items: List<Movie?>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding: AdapterCarouselBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_carousel, parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
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

class CarouselViewHolder(private val binding: AdapterCarouselBinding) : RecyclerView.ViewHolder(binding.root){

    fun bindData(model: Movie?, context: Context){
        val viewModel = CarouselItemViewModel(model)
        binding.itemCarousel = viewModel
        itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("movie_id", model?.id.toString())
            context.startActivity(intent)
        }
    }



}