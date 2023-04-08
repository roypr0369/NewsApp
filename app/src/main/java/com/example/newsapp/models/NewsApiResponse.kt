package com.example.newsapp.models

class NewsApiResponse : java.io.Serializable{
    lateinit var status: String
    var totalResults: Int = 0
    lateinit var articles: List<NewsHeadlines>
    constructor()
    constructor(status: String?, totalResults: Int, articles: List<NewsHeadlines>){
        this.status = status ?: ""
        this.totalResults = totalResults
        this.articles = articles
    }
}