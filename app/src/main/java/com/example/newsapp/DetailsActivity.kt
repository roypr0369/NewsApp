package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.models.NewsHeadlines
import com.example.newsapp.models.Source
import com.squareup.picasso.Picasso


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = layoutInflater.inflate(R.layout.activity_details, null)
        setContentView(root)
        val txt_title = findViewById<TextView>(R.id.text_title)
        val txt_author = findViewById<TextView>(R.id.text_detail_author)
        val txt_time = findViewById<TextView>(R.id.text_detail_time)
        val txt_detail = findViewById<TextView>(R.id.text_detail_detail)
        val txt_content = findViewById<TextView>(R.id.text_detail_content)
        val img_news = findViewById<ImageView>(R.id.img_detail_news)

        val headlines = (intent.getSerializableExtra("data") as? NewsHeadlines) ?: NewsHeadlines(
            Source(),
            "",
            "" ,
            "",
            "",
            "",
            "",
            ""
        )

        txt_title.text = headlines.title
        txt_author.text = headlines.author
        txt_time.text = headlines.publishedAt
        txt_detail.text = headlines.description
        txt_content.text = headlines.content
        Picasso.get().load(headlines.urlToImage).into(img_news)

    }
}