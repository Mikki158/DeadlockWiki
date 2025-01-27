package com.example.navigationmenu.ui.heroes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationmenu.R
import com.example.navigationmenu.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroesFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var heroesAdapter: HeroesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_heroes, container, false)
        Log.i("11", "11")
        //recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 3) // 3 столбца

        // Получение данных о героях из БД
        Log.i("22", "22")
        getHeroesFromAPI()

        // Передача данных адаптеру

        return view
    }

    private fun getHeroesFromAPI() {
        Log.i("33", "33")
        RetrofitClient.instance.getHeroes().enqueue(object : Callback<List<HeroInfo>> {
            override fun onResponse(call: Call<List<HeroInfo>>, response: Response<List<HeroInfo>>) {
                if (response.isSuccessful) {
                    Log.i("99", "99")
                    val heroes = response.body()
                    if (heroes != null) {
                        Log.i("00", "00")
                        heroesAdapter = HeroesAdapter(heroes) { hero ->
                            //val heroDetailFragment = HeroDetailFragment.newInstance(hero.name, hero.imageUrl, hero.description)

                            // Заменяем текущий фрагмент на HeroDetailFragment
                            //requireActivity().supportFragmentManager.beginTransaction()
                            //    .replace(R.id.fragment_container, heroDetailFragment)
                            //    .addToBackStack(null)  // Добавляем в backstack для возможности возврата
                            //    .commitAllowingStateLoss()
                        }
                        recyclerView.adapter = heroesAdapter
                    } else {
                        Log.i("22", "22")
                    }
                } else {
                    Log.i("33", "33")
                }
            }

            override fun onFailure(call: Call<List<HeroInfo>>, t: Throwable) {
                Log.e("onFailure", "Ошибка запроса: ${t.message}")
            }
        })
    }
}