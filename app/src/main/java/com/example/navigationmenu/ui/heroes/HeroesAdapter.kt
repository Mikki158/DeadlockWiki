package com.example.navigationmenu.ui.heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigationmenu.R
import com.example.navigationmenu.ui.heroes.HeroInfo

class HeroesAdapter(private val heroes: List<HeroInfo>, private val onHeroClick: (HeroInfo) -> Unit) : RecyclerView.Adapter<HeroesAdapter.HeroViewHolder>() {

    class HeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val heroImage: ImageView = view.findViewById(R.id.hero_image)
        val heroName: TextView = view.findViewById(R.id.hero_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_list_item, parent, false)
        return HeroViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = heroes[position]
        holder.heroName.text = hero.name

        // Используем Glide для загрузки изображения
        Glide.with(holder.itemView.context)
            .load(hero.imageUrl)
            .placeholder(R.drawable.placeholder)
            .into(holder.heroImage)

        holder.itemView.setOnClickListener {
            onHeroClick(hero)
        }
    }

    override fun getItemCount(): Int {
        return heroes.size
    }
}

