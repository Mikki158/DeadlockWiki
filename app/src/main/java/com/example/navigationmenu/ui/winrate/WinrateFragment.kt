package com.example.navigationmenu.ui.winrate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.navigationmenu.databinding.FragmentWinrateBinding
import com.example.navigationmenu.R
import com.example.navigationmenu.ui.winrate.Hero
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.navigationmenu.network.RetrofitClient
import android.util.Log
import androidx.core.content.ContextCompat
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity


class WinrateFragment : Fragment() {
    private lateinit var tableLayout: TableLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_winrate, container, false)
        tableLayout = view.findViewById(R.id.statsTable)
        Log.i("12", "12")
        getWinrateFromAPI()
        return view
    }
    private fun getWinrateFromAPI() {
        Log.i("88", "88")
        RetrofitClient.instance.getWinrate().enqueue(object : Callback<List<Hero>> {
            override fun onResponse(call: Call<List<Hero>>, response: Response<List<Hero>>) {
                if (response.isSuccessful) {
                    Log.i("99", "99")
                    val heroes = response.body()
                    if (heroes != null) {
                        Log.i("00", "00")
                        updateTable(heroes)
                    } else {
                        Log.i("22", "22")
                    }
                } else {
                    Log.i("33", "33")
                }
            }
            override fun onFailure(call: Call<List<Hero>>, t: Throwable) {
                Log.e("onFailure", "Ошибка запроса: ${t.message}")
            }
        })
    }
    private fun updateTable(heroes: List<Hero>) {
        for (hero in heroes) {
            val row = TableRow(context)
            val name = createCell(hero.name)
            val nameTextView = TextView(context)
            nameTextView.text = hero.name
            nameTextView.setTextColor(Color.parseColor("#000000"))
            val winrate = createCell(hero.winrate)
            val winrateTextView = TextView(context)
            winrateTextView.text = hero.winrate
            winrateTextView.setTextColor(Color.parseColor("#000000"))
            val pickrate = createCell(hero.pickrate)
            val pickrateTextView = TextView(context)
            pickrateTextView.text = hero.pickrate
            pickrateTextView.setTextColor(Color.parseColor("#000000"))
            val kda = createCell(hero.kda.toString())
            val kdaTextView = TextView(context)
            kdaTextView.text = hero.kda.toString()
            kdaTextView.setTextColor(Color.parseColor("#000000"))
            row.apply {
                addView(name)
                addView(winrate)
                addView(pickrate)
                addView(kda)
            }
            tableLayout.addView(row)
        }
    }
    private fun createCell(text: String): TextView {
        return TextView(context).apply {
            this.text = text
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            setPadding(8, 8, 8, 8)
            gravity = Gravity.CENTER
            this.setTextColor(Color.parseColor("#000000"))
            this.setBackgroundResource(R.drawable.cell_border)
        }
    }
}