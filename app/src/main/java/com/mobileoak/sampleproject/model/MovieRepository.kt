package com.mobileoak.sampleproject.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.mobileoak.sampleproject.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class MovieRepository @Inject constructor() {
    private val TAG = MovieRepository::class.simpleName

    private val movieListCache = HashMap<Int, List<Movie>>()

    private val API_KEY = "8b2abf063dfc9dd2ce0841c68fd7e56c"

    suspend fun getMovies(page: Int = 1): List<Movie> {
        val cachedList = movieListCache[page]
        return if (cachedList != null) {
            Log.e(TAG, "Returning cached list")
            cachedList
        } else {
            Log.e(TAG, "List is empty, calling network")
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

class Movie {
    @SerializedName("title")
    var title: String = ""
    @SerializedName("overview")
    var overview: String = ""
    @SerializedName("poster_path")
    var imageURL: String = ""
}