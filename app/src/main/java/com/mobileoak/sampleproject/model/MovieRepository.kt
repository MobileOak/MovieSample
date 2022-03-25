package com.mobileoak.sampleproject.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.mobileoak.sampleproject.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class MovieRepository @Inject constructor() {
    private val TAG = MovieRepository::class.simpleName

    // This isn't the best way to cache this, since the page size could change,
    // the order could change, or any number of other things, but it's here to demonstrate
    // where I would implement caching.
    private val movieListCache = HashMap<Int, List<Movie>>()

    private val API_KEY = "8b2abf063dfc9dd2ce0841c68fd7e56c"

    // The code to support getting multiple pages of movies is partially completed
    // but I would need to hook the data adapter to notify the view model to request more
    suspend fun getMovies(page: Int = 1): List<Movie> {
        val cachedList = movieListCache[page]
        return if (cachedList != null) {
            Log.i(TAG, "Returning cached list")
            cachedList
        } else {
            Log.i(TAG, "List is empty, calling network")
            val list = requestMovies(page)
            movieListCache[page] = list
            list
        }
    }

    private suspend fun requestMovies(page: Int) = suspendCoroutine<List<Movie>> { cont ->
        val apiInterface = ApiInterface.create().getMovies(API_KEY, page)

        apiInterface.enqueue( object: Callback<NetworkRequestObject> {
            override fun onResponse(
                call: Call<NetworkRequestObject>,
                response: Response<NetworkRequestObject>
            ) {
                val body = response.body()
                if (body != null) {
                    val movieList = mutableListOf<Movie>()
                    movieList.addAll(body.results)
                    cont.resume(movieList)
                } else {
                    cont.resumeWithException(IllegalStateException("Empty body"))
                }
            }

            override fun onFailure(call: Call<NetworkRequestObject>, t: Throwable) {
                Log.e(TAG, "Network request error is: " + t.message)
                cont.resumeWithException(t)
            }

        })
    }
}

class NetworkRequestObject {
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("results")
    var results = ArrayList<Movie>()
}


class Movie: Serializable {
    @SerializedName("title")
    var title: String = ""
    @SerializedName("overview")
    var overview: String = ""
    @SerializedName("poster_path")
    var imageURL: String = ""

    val fullImageUrl: String
        get() {
            return "https://image.tmdb.org/t/p/original/$imageURL"
        }
}