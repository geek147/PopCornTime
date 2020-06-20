package com.envios.kitabisa.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.envios.kitabisa.R
import com.envios.kitabisa.data.remote.model.Review
import com.envios.kitabisa.databinding.ReviewAdapterBinding


class ReviewAdapter(private val context: Context): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){
    private var listReview: MutableList<Review?>? = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ReviewViewHolder{
        val binding: ReviewAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.review_adapter, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (listReview.isNullOrEmpty()) {
            0
        } else{
            listReview!!.size
        }
    }

    fun addData(list: List<Review?>?){
        if (list != null) {
            this.listReview?.addAll(list)
        }
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        listReview?.get(holder.adapterPosition)?.let {
            holder.bindData(it)
        }
    }



    class ReviewViewHolder(private val binding: ReviewAdapterBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindData(model: Review){
            val viewModel = ReviewItemViewModel(model)
            binding.itemReview = viewModel

        }



    }


}