package com.example.newsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.models.NewsHeadlines
import com.squareup.picasso.Picasso

class CustomAdapter(
    private var context: Context,
    private var headlines: List<NewsHeadlines>,
    private var listener: SelectListener): RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false))
    }

    override fun getItemCount(): Int {
        return headlines.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.text_title.text = headlines[position].title
        holder.text_source.text = headlines[position].source?.name
        if(headlines[position].urlToImage != null){
            Picasso.get().load(headlines[position].urlToImage).into(holder.img_headline)
        }

        holder.cardView.setOnClickListener {
            listener.onNewsClicked(headlines[position])
        }
    }
}