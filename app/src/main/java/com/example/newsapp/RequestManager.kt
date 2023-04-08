package com.example.newsapp

import android.content.Context
import android.widget.Toast
import com.example.newsapp.models.NewsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RequestManager {
    lateinit var context: Context
    var retrofit: Retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/").addConverterFactory(GsonConverterFactory.create()).build()
    constructor()
    constructor(context: Context){
        this.context = context
    }

    fun getNewsHeadlines(listener: OnFetchDataListener<NewsApiResponse>, category: String, query: String?){
        val callNewsApi: CallNewsApi = retrofit.create(CallNewsApi::class.java)
        val call :Call<NewsApiResponse> = callNewsApi.callHeadlines(country = "in", category,
            query!!, context.getString(R.string.api_key))
        try{
            call.enqueue(object: Callback<NewsApiResponse> {
                override fun onResponse(
                    call: Call<NewsApiResponse>,
                    response: Response<NewsApiResponse>,
                ) {
                    if(!response.isSuccessful){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }

                    listener.onFetchData(response.body()!!.articles, response.message())
                }

                override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                    listener.onError("Request Failed!")
                }

            })
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    interface CallNewsApi{
        @GET("top-headlines")
        fun callHeadlines(
                @Query("country") country: String,
                @Query("category") category: String,
                @Query("q") query: String,
                @Query("apiKey") api_key: String
        ): Call<NewsApiResponse>
    }
}