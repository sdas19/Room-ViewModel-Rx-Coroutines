package com.example.githubsampleapplication

import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imageUrl")
fun loadImage(view: CircleImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).placeholder(R.drawable.circular_shape).into(view)
}