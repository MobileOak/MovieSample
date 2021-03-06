package com.mobileoak.sampleproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobileoak.sampleproject.R
import com.mobileoak.sampleproject.model.Movie

class DataAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    private var dataset: List<Movie> = emptyList()

    class MyViewHolder(layout: LinearLayout) : RecyclerView.ViewHolder(layout) {
        val name: TextView = layout.findViewById(R.id.item_name)
        val description: TextView = layout.findViewById(R.id.item_description)
        val itemImage: ImageView = layout.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false) as LinearLayout
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val name = dataset[position].title
        val description = dataset[position].overview
        val fullImageURL = dataset[position].fullImageUrl

        holder.name.text = name
        holder.description.text = description

        holder.itemView.setOnClickListener {
            listener(dataset[position])
        }

        Glide.with(holder.itemView.context).load(fullImageURL).into(holder.itemImage)
    }

    fun updateData(dataset: List<Movie>) {
        this.dataset = dataset
    }

    override fun getItemCount() = dataset.size

}