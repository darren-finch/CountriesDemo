package com.darrenfinch.countriesdemo.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.darrenfinch.countriesdemo.R

fun getProgressDrawable(context: Context) : CircularProgressDrawable
{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}
fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable)
{
    val requestOptions = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(this.context).setDefaultRequestOptions(requestOptions).load(url).into(this)
}