package com.example.navigationmenu.network

import com.example.navigationmenu.ui.heroes.Ability
import com.example.navigationmenu.ui.heroes.AbilityUrl
import com.example.navigationmenu.ui.winrate.Hero
import com.example.navigationmenu.ui.heroes.HeroInfo
import com.example.navigationmenu.ui.news.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("heroes_stats")
    fun getWinrate(): Call<List<Hero>>

    @GET("heroes")
    fun getHeroes(): Call<List<HeroInfo>>

    @GET("abilities")
    fun getAbilities(@Query("heroid") heroId: String?) : Call<List<AbilityUrl>>

    @GET("ability")
    fun getAbility(@Query("heroid") heroId: String?, @Query("ability_number") abilityNumber: String?) : Call<List<Ability>>

    @GET("news")
    fun getNews(): Call<List<News>>
}
