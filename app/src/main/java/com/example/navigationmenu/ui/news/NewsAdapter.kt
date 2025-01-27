package com.example.navigationmenu.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.util.Log
import com.example.navigationmenu.R

class NewsAdapter(private val newsList: List<News>, private val onNewsClick: (News) -> Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImage: ImageView = view.findViewById(R.id.news_image)
        val title: TextView = view.findViewById(R.id.news_title)
        val subtitle: TextView = view.findViewById(R.id.news_subtitle)
        val date: TextView = view.findViewById(R.id.news_date_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.new_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.i("test3", "test3")
        val news = newsList[position]
        holder.title.text = news.title
        //holder.title.autoLinkMask = news.link

        holder.subtitle.text = news.description
        holder.date.text = news.date

        // Используем Glide для загрузки изображения
        Glide.with(holder.itemView.context)
            .load(news.imageUrl)
            .placeholder(R.drawable.placeholder)
            .into(holder.newsImage)

        holder.itemView.setOnClickListener {
            onNewsClick(news)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}