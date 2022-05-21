package com.example.ecommerceuserbatch03.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.ecommerceuserbatch03.R

@BindingAdapter("app:setImageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.placeholder_image)
            .into(imageView)
    }
}
