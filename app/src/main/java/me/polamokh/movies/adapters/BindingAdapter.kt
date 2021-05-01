package me.polamokh.movies.adapters

import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import me.polamokh.movies.R
import me.polamokh.movies.domain.Genre

@BindingAdapter("image")
fun ImageView.bindImage(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_image_not_supported)
            .centerCrop()
            .into(this)
    }
}

@BindingAdapter("genres")
fun TextView.bindGenres(genres: List<Genre>?) {
    genres?.let {
        text = TextUtils.join(", ", genres.map { genre -> genre.name })
    }
}