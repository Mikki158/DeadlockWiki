package com.example.navigationmenu.ui.heroes

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigationmenu.R
import com.example.navigationmenu.network.RetrofitClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroListFragment : Fragment() {

    private lateinit var heroAdapter: HeroesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var heroDetailContainer: FrameLayout
    private lateinit var backgroundImage: ImageView
    private lateinit var ability: LinearLayout
    private lateinit var currentHero: HeroInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_heroes, container, false)
        val viewBackground = inflater.inflate(R.layout.content_main, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        heroDetailContainer = view.findViewById(R.id.hero_detail_container)
        backgroundImage = viewBackground.findViewById(R.id.imageView3)
        ability = view.findViewById(R.id.abilities)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        getHeroesFromAPI()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.top_right_button)
        val buttonAbility1: ImageView = view.findViewById(R.id.ability_1)
        val buttonAbility2: ImageView = view.findViewById(R.id.ability_2)
        val buttonAbility3: ImageView = view.findViewById(R.id.ability_3)
        val buttonAbility4: ImageView = view.findViewById(R.id.ability_4)
        val goneAbility: Button = view.findViewById(R.id.ability_gone)
        button.setOnClickListener {
            heroDetailContainer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
        buttonAbility1.setOnClickListener {
            updateAbility("1")
            ability.visibility = View.VISIBLE
            heroDetailContainer.visibility = View.GONE
        }
        buttonAbility2.setOnClickListener {
            updateAbility("2")
            ability.visibility = View.VISIBLE
            heroDetailContainer.visibility = View.GONE
        }
        buttonAbility3.setOnClickListener {
            updateAbility("3")
            ability.visibility = View.VISIBLE
            heroDetailContainer.visibility = View.GONE
        }
        buttonAbility4.setOnClickListener {
            updateAbility("4")
            ability.visibility = View.VISIBLE
            heroDetailContainer.visibility = View.GONE
        }
        goneAbility.setOnClickListener{
            ability.visibility = View.GONE
            heroDetailContainer.visibility = View.VISIBLE
            ability.findViewById<LinearLayout>(R.id.distance).visibility = View.GONE
            ability.findViewById<LinearLayout>(R.id.range).visibility = View.GONE
            ability.findViewById<LinearLayout>(R.id.duration).visibility = View.GONE
            ability.findViewById<LinearLayout>(R.id.chargeres).visibility = View.GONE
            ability.findViewById<LinearLayout>(R.id.chargeres_time).visibility = View.GONE
            ability.findViewById<LinearLayout>(R.id.cooldown).visibility = View.GONE
            ability.findViewById<LinearLayout>(R.id.characteristic_1).visibility = View.GONE
            ability.findViewById<LinearLayout>(R.id.characteristic_2).visibility = View.GONE
            ability.findViewById<LinearLayout>(R.id.characteristic_3).visibility = View.GONE
        }
    }
    private fun updateAbility(number_ability: String) {
        RetrofitClient.instance.getAbility(currentHero.id, number_ability).enqueue(object : Callback<List<Ability>> {
            override fun onResponse(call: Call<List<Ability>>, response: Response<List<Ability>>) {
                if (response.isSuccessful) {
                    val abilitiy = response.body()
                    if(abilitiy != null) {
                        val ability_icon = ability.findViewById<ImageView>(R.id.ability_icon)
                        Glide.with(this@HeroListFragment).load(abilitiy[0].ability_url).into(ability_icon)
                        val ability_name = ability.findViewById<TextView>(R.id.ability_name)
                        ability_name.text = abilitiy[0].ability_name
                        if(abilitiy[0].distance != null) {
                            ability.findViewById<LinearLayout>(R.id.distance).visibility = View.VISIBLE
                            val distance_text = ability.findViewById<TextView>(R.id.distance_text)
                            distance_text.text = abilitiy[0].distance
                        }
                        if(abilitiy[0].ability_range != null) {
                            ability.findViewById<LinearLayout>(R.id.range).visibility = View.VISIBLE
                            val range_text = ability.findViewById<TextView>(R.id.range_text)
                            range_text.text = abilitiy[0].ability_range
                        }
                        if(abilitiy[0].duration != null) {
                            ability.findViewById<LinearLayout>(R.id.duration).visibility = View.VISIBLE
                            val duration_text = ability.findViewById<TextView>(R.id.duration_text)
                            duration_text.text = abilitiy[0].duration
                        }
                        if(abilitiy[0].charges != null){
                            ability.findViewById<LinearLayout>(R.id.chargeres).visibility = View.VISIBLE
                            val chargeres_text = ability.findViewById<TextView>(R.id.chargeres_text)
                            chargeres_text.text = abilitiy[0].charges
                        }
                        if(abilitiy[0].charging_time != null) {
                            ability.findViewById<LinearLayout>(R.id.chargeres_time).visibility = View.VISIBLE
                            val chargeres_time_text = ability.findViewById<TextView>(R.id.chargeres_time_text)
                            chargeres_time_text.text = abilitiy[0].charging_time
                        }
                        if(abilitiy[0].recharge != null) {
                            ability.findViewById<LinearLayout>(R.id.cooldown).visibility = View.VISIBLE
                            val cooldown_text = ability.findViewById<TextView>(R.id.cooldown_text)
                            cooldown_text.text = abilitiy[0].recharge
                        }
                        val description = ability.findViewById<TextView>(R.id.description)
                        description.text = abilitiy[0].description
                        if(abilitiy[0].characteristic_1 != null) {
                            ability.findViewById<LinearLayout>(R.id.characteristic_1).visibility = View.VISIBLE
                            val characteristic_1 = abilitiy[0].characteristic_1.split("\n").toTypedArray()
                            ability.findViewById<TextView>(R.id.characteristic_1_1).text = characteristic_1[0]
                            ability.findViewById<TextView>(R.id.characteristic_1_2).text = characteristic_1[1]
                        }
                        if(abilitiy[0].characteristic_2 != null) {
                            ability.findViewById<LinearLayout>(R.id.characteristic_2).visibility = View.VISIBLE
                            val characteristic_2 = abilitiy[0].characteristic_2.split("\n").toTypedArray()
                            ability.findViewById<TextView>(R.id.characteristic_2_1).text = characteristic_2[0]
                            ability.findViewById<TextView>(R.id.characteristic_2_2).text = characteristic_2[1]
                        }
                        if(abilitiy[0].characteristic_3 != null) {
                            ability.findViewById<LinearLayout>(R.id.characteristic_3).visibility = View.VISIBLE
                            val characteristic_3 = abilitiy[0].characteristic_3.split("\n").toTypedArray()
                            ability.findViewById<TextView>(R.id.characteristic_3_1).text = characteristic_3[0]
                            ability.findViewById<TextView>(R.id.characteristic_3_2).text = characteristic_3[1]
                        }
                        ability.findViewById<TextView>(R.id.upgrade_1).text = abilitiy[0].upgrade_1
                        ability.findViewById<TextView>(R.id.upgrade_2).text = abilitiy[0].upgrade_2
                        ability.findViewById<TextView>(R.id.upgrade_3).text = abilitiy[0].upgrade_3
                    }
                }
            }
            override fun onFailure(call: Call<List<Ability>>, t: Throwable) {
                Log.e("onFailure", "Ошибка запроса: ${t.message}")
            }
        })
    }
    private fun getHeroesFromAPI() {
        RetrofitClient.instance.getHeroes().enqueue(object : Callback<List<HeroInfo>> {
            override fun onResponse(call: Call<List<HeroInfo>>, response: Response<List<HeroInfo>>) {
                if (response.isSuccessful) {
                    val heroes = response.body()
                    if (heroes != null) {
                        setupAdapter(heroes)
                    }
                }
            }
            override fun onFailure(call: Call<List<HeroInfo>>, t: Throwable) {
                Log.e("onFailure", "Ошибка запроса: ${t.message}")
            }
        })
    }

    private fun setupAdapter(heroes: List<HeroInfo>) {
        heroAdapter = HeroesAdapter(heroes) { hero ->
            showHeroDetail(hero)
        }
        Log.i("Hero1", "Hero1")
        recyclerView.adapter = heroAdapter
        val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        recyclerView.addItemDecoration(SpacingItemDecoration(spaceInPixels, spaceInPixels))
    }
    private fun showHeroDetail(hero: HeroInfo) {
        RetrofitClient.instance.getAbilities(hero.id).enqueue(object : Callback<List<AbilityUrl>> {
            override fun onResponse(call: Call<List<AbilityUrl>>, response: Response<List<AbilityUrl>>) {
                if (response.isSuccessful) {
                    val abilities = response.body()
                    if (abilities != null) {
                        val image1 = heroDetailContainer.findViewById<ImageView>(R.id.ability_1)
                        val image2 = heroDetailContainer.findViewById<ImageView>(R.id.ability_2)
                        val image3 = heroDetailContainer.findViewById<ImageView>(R.id.ability_3)
                        val image4 = heroDetailContainer.findViewById<ImageView>(R.id.ability_4)
                        Glide.with(this@HeroListFragment).load(abilities[0].ability_url).into(image1)
                        Glide.with(this@HeroListFragment).load(abilities[1].ability_url).into(image2)
                        Glide.with(this@HeroListFragment).load(abilities[2].ability_url).into(image3)
                        Glide.with(this@HeroListFragment).load(abilities[3].ability_url).into(image4)
                    }
                }
            }
            override fun onFailure(call: Call<List<AbilityUrl>>, t: Throwable) {
                Log.e("onFailure", "Ошибка запроса: ${t.message}")
            }
        })
        val heroURL = hero.imageUrl
        recyclerView.visibility = View.GONE
        currentHero = hero
        backgroundImage.visibility = View.GONE
        heroDetailContainer.visibility = View.VISIBLE
        val heroImageView = heroDetailContainer.findViewById<ImageView>(R.id.hero_image)
        val heroNameTextView = heroDetailContainer.findViewById<TextView>(R.id.hero_name)
        val heroDescriptionTextView = heroDetailContainer.findViewById<TextView>(R.id.hero_description)
        Glide.with(this).load(hero.imageUrl).into(heroImageView)
        heroNameTextView.text = hero.name
        heroDescriptionTextView.text = hero.description
    }
}
class SpacingItemDecoration(private val horizontalSpacing: Int, private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = horizontalSpacing / 2
        outRect.right = horizontalSpacing / 2
        outRect.top = verticalSpacing / 2
        outRect.bottom = verticalSpacing / 2
    }
}