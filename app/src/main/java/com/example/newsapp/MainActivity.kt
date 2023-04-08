package com.example.newsapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.models.NewsApiResponse
import com.example.newsapp.models.NewsHeadlines

class MainActivity : AppCompatActivity() , SelectListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = ProgressDialog(this)
        dialog.setTitle("Fetching news article...")
        dialog.show()

        val manager = RequestManager(this)
        manager.getNewsHeadlines(listener, category = "general", query = "")
    }

    private var listener: OnFetchDataListener<NewsApiResponse> = object : OnFetchDataListener<NewsApiResponse>{
        override fun onFetchData(list: List<NewsHeadlines>, message: String) {
            showNews(list)
            dialog.dismiss()
        }

        override fun onError(message: String) {
            TODO("Not yet implemented")
        }

    }

    private fun showNews(list: List<NewsHeadlines>) {
        recyclerView = findViewById(R.id.recycler_main)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this,1)
        adapter = CustomAdapter(this, list, this)
        recyclerView.adapter = adapter

    }

    override fun onNewsClicked(headlines: NewsHeadlines) {
        startActivity(Intent(this, DetailsActivity::class.java)
            .putExtra("data", headlines))
    }
}