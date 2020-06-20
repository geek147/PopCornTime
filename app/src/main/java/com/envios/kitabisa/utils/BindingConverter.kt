package com.envios.kitabisa.utils


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.envios.kitabisa.R

object BindingConverters {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(imageView: ImageView, url: String?){
        Glide.with(imageView.context)
            .asBitmap()
            .load(url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background))
                    .format(DecodeFormat.PREFER_RGB_565)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView)
    }


}