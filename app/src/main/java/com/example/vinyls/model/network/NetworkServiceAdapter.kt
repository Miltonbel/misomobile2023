package com.example.vinyls.model.network

import android.content.Context
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinyls.model.AlbumDBDao
import com.google.gson.Gson

class NetworkServiceAdapter constructor(context: Context) {


    companion object{
        const val BASE_URL= "http://10.0.2.2/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun getAlbums(onComplete:(resp:List<AlbumDBDao>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            { response ->
                val arrayAlbumDBDao: Array<AlbumDBDao> =Gson().fromJson(response, Array<AlbumDBDao>::class.java)
                onComplete(arrayAlbumDBDao.asList())
            },
            {
                onError(it)
            }))
    }

    fun postAlbum(onComplete: (AlbumDBDao) -> Unit, onError: (error:VolleyError) -> Unit, body:JSONObject){
        requestQueue.add(postRequest("albums",
            body,
            { response ->
                val albumResponse = Gson().fromJson(response.toString(), AlbumDBDao::class.java)
                onComplete(albumResponse)
            },
            {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL +path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL +path, body, responseListener, errorListener)
    }
}