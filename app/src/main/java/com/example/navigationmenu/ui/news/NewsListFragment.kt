package com.example.navigationmenu.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationmenu.R
import com.example.navigationmenu.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListFragment : Fragment(){

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsList: List<News>
    private var filteredNewsList = mutableListOf<News>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_news)

        recyclerView.layoutManager = GridLayoutManager(context, 1)

        getNewsFromAPI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.search_button)

        button.setOnClickListener {
            Log.i("search", "search")
            val search = view.findViewById<EditText>(R.id.search_edit_text)
            filterNews(search.getText().toString())
        }
    }

    private fun getNewsFromAPI() {
        RetrofitClient.instance.getNews().enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                if (response.isSuccessful) {
                    val news = response.body()
                    if (news != null) {
                        newsList = news
                        Adapter(news)
                        Log.i("test", "test")
                    }
                }
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                Log.e("onFailure", "Ошибка запроса: ${t.message}")
            }
        })
    }

    private fun Adapter(newss: List<News>) {
        newsAdapter = NewsAdapter(newss) { news ->
            Log.i("View", "news")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
            startActivity(intent)
        }
        Log.i("View2", "View2")
        recyclerView.adapter = newsAdapter
    }

    private fun filterNews(query: String) {
        Log.i("search", query)

        val filteredNews =newsList.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }

        filteredNewsList.clear()
        filteredNewsList.addAll(filteredNews)

        newsAdapter = NewsAdapter(filteredNews) { news ->
            Log.i("View", "news")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
            startActivity(intent)
        }
        Log.i("View2", "View2")
        recyclerView.adapter = newsAdapter
    }
}