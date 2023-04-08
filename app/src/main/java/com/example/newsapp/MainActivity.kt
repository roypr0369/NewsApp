package com.example.newsapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.models.NewsApiResponse
import com.example.newsapp.models.NewsHeadlines

class MainActivity : AppCompatActivity() , SelectListener , View.OnClickListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var dialog: ProgressDialog
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView= findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                dialog.setTitle("Fetching news articles of $query")
                dialog.show()
                val manager = RequestManager(this@MainActivity)
                manager.getNewsHeadlines(listener, category = "general", query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        dialog = ProgressDialog(this)
        dialog.setTitle("Fetching news article...")
        dialog.show()

        btn1 = findViewById(R.id.btn1)
        btn1.setOnClickListener(this)
        btn2 = findViewById(R.id.btn2)
        btn2.setOnClickListener(this)
        btn3 = findViewById(R.id.btn3)
        btn3.setOnClickListener(this)
        btn4 = findViewById(R.id.btn4)
        btn4.setOnClickListener(this)
        btn5 = findViewById(R.id.btn5)
        btn5.setOnClickListener(this)
        btn6 = findViewById(R.id.btn6)
        btn6.setOnClickListener(this)
        btn7 = findViewById(R.id.btn7)
        btn7.setOnClickListener(this)


        val manager = RequestManager(this)
        manager.getNewsHeadlines(listener, category = "general", query = "")
    }

    private var listener: OnFetchDataListener<NewsApiResponse> = object : OnFetchDataListener<NewsApiResponse>{
        override fun onFetchData(list: List<NewsHeadlines>, message: String) {
            if(list.isEmpty()){
                Toast.makeText(this@MainActivity, "No data found!!", Toast.LENGTH_SHORT).show()
            }else{
                showNews(list)
                dialog.dismiss()
            }
        }

        override fun onError(message: String) {
            Toast.makeText(this@MainActivity, "Error Occurred!!", Toast.LENGTH_SHORT).show()
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

    override fun onClick(v: View?) {
        val button = v as Button
        val category = button.text.toString()
        dialog.setTitle("Fetching news articles of $category")
        dialog.show()
        val manager = RequestManager(this)
        manager.getNewsHeadlines(listener, category = category, query = "")
    }
}