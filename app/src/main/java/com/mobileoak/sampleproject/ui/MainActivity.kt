package com.mobileoak.sampleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileoak.sampleproject.R
import com.mobileoak.sampleproject.model.Movie
import com.mobileoak.sampleproject.viewmodel.MainState
import com.mobileoak.sampleproject.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DataAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var loadingTextView: TextView
    private lateinit var errorTextView: TextView

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = DataAdapter(emptyList(), this)

        loadingTextView = findViewById(R.id.loading_text)
        errorTextView = findViewById(R.id.error_text)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use a linear layout manager
            layoutManager = viewManager

            adapter = viewAdapter
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                MainState.Empty -> onEmptyData()
                is MainState.DataLoaded -> onDataLoaded(state.data)
                is MainState.Error -> onError(state.message)
            }

        }
    }

    private fun onEmptyData() {
        loadingTextView.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun onDataLoaded(data: List<Movie>) {
        recyclerView.visibility = View.VISIBLE
        loadingTextView.visibility = View.GONE
        errorTextView.visibility = View.GONE

        viewAdapter.updateData(data)
        viewAdapter.notifyDataSetChanged()
    }

    private fun onError(error: String) {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = error
        recyclerView.visibility = View.GONE
        loadingTextView.visibility = View.GONE
    }
}