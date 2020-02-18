package com.basheathini.catfacts

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var catViewModel: CatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        val adapter = CatListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        catViewModel = ViewModelProvider(this).get(CatViewModel::class.java)

        //when there's change in the data the ui gets updated. the catViewModel holds the data.
        catViewModel.allCats.observe(this, Observer { cats ->
            cats?.let { adapter.setCats(it) }
        })

        if (isConnected){
            launch { loadData() }
        }
    }

    //this can be implemented more nicer using retrofit, which will avoid the the looping.
    // since i'm using only one endpoint
    //i have decided to implement it this way.
    private fun loadData() {
        val catArray = ArrayList<Cat>()

        val request = Request.Builder().url("https://cat-fact.herokuapp.com/facts").build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val facts = JSONObject(body.toString())
                val array : JSONArray = facts.getJSONArray("all")
                val size: Int = array.length()

                for (i in 0 until size) {
                    val catFact : JSONObject = array.getJSONObject(i)
                    val model = Cat(catFact.getString("text"))
                    catArray.add(model)
                }
                catViewModel.insert(catArray)
            }
            override fun onFailure(call: Call?, e: IOException?) {
                //if the call to the api fails, try calling again
                loadData()
            }
        })
    }
}
