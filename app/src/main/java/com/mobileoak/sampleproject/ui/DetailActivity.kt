package com.mobileoak.sampleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mobileoak.sampleproject.R
import com.mobileoak.sampleproject.model.Movie

class DetailActivity : AppCompatActivity() {

    // If this Activity had actual responsibilities I would add a ViewModel to it to isolate the
    // business logic
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.getSerializableExtra("Movie") as Movie

        val movieTitle = findViewById<TextView>(R.id.detail_movie_title)
        movieTitle.text = movie.title

        val movieDescription = findViewById<TextView>(R.id.detail_movie_description)
        movieDescription.text = movie.overview

        val movieImage = findViewById<ImageView>(R.id.detail_movie_image)

        Glide.with(this).load(movie.fullImageUrl).into(movieImage)

    }
}