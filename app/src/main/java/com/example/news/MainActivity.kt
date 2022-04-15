package com.example.news

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // calling recyclerView
  recyclerView.layoutManager = LinearLayoutManager(this)
      FetchData()
    mAdapter  = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }
     private fun FetchData()
     {
         val url ="https://saurav.tech/NewsAPI/everything/cnn.json"

         val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
             {
               val newsJsonArray = it.getJSONArray("articles")
                 val newsArray = ArrayList<News>()
                 for(i in 0 until newsJsonArray.length() )
                 {
                     val newsjsonobject = newsJsonArray.getJSONObject(i)
                     val news = News(
                         newsjsonobject.getString("title"),
                         newsjsonobject.getString("author"),
                         newsjsonobject.getString("url"),
                         newsjsonobject.getString("urlToImage"),
                     )
                     newsArray.add(news)
                 }
                 mAdapter.updateNews(newsArray)
             },
             { error ->
                 // TODO: Handle error
             }
         )

// Access the RequestQueue through your singleton class.
         MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
     }


    override fun OnItemClicked(item: News) {

     val builder = CustomTabsIntent.Builder();
       val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}