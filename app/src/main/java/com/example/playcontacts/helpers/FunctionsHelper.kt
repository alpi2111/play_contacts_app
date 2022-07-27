package com.example.playcontacts.helpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.playcontacts.R
import com.example.playcontacts.models.ErrorModel
import com.google.gson.Gson
import retrofit2.Response

object FunctionsHelper {

    private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

    fun errorResponseToObject(response: Response<*>): ErrorModel {
        val gson = Gson()
        return gson.fromJson(
            response.errorBody()!!.charStream(),
            ErrorModel::class.java
        )
    }

    fun glideBuilder(context: Context, urlImagen: String, imageView: ImageView) {
        Glide.with(context).load(urlImagen)
            .thumbnail(Glide.with(context).load(R.drawable.loading_gif).fitCenter()).fitCenter()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .into(imageView)
    }
}