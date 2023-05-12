package com.example.vinyls.model.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinyls.model.AlbumDBDao
import com.example.vinyls.model.Artist
import com.example.vinyls.model.AlbumDetail
import com.example.vinyls.model.ArtistDetail
import com.example.vinyls.model.Track
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "http://35.209.15.30/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun getAlbums(onComplete:(resp:List<AlbumDBDao>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<AlbumDBDao>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, AlbumDBDao(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    fun getArtists(onComplete:(resp:List<Artist>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Artist(
                        artistId = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        birthDate = item.getString("birthDate"),
                        description = item.getString("description")
                    ))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    fun getArtistDetail(artistId:Int, onComplete:(resp:List<ArtistDetail>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("musicians/$artistId",
            { response ->
                val resp = JSONObject(response)
                val list = mutableListOf<ArtistDetail>()
                val item: JSONObject = resp

                val albumsArray = item.getJSONArray("albums")
                val albums = mutableListOf<AlbumDBDao>()
                for (i in 0 until albumsArray.length()) {
                    val trackObject = albumsArray.getJSONObject(i)
                    val track = AlbumDBDao(
                        albumId = trackObject.getInt("id"),
                        name = trackObject.getString("name"),
                        cover = trackObject.getString("cover"),
                        recordLabel = item.getString("name"),
                        releaseDate = item.getString("name"),
                        genre = item.getString("name"),
                        description = item.getString("description"))
                    albums.add(track)
                }

                val fullDate = item.getString("birthDate")
                val shortDate = fullDate.substring(0, 10)

                list.add(0, ArtistDetail(
                    artistId = item.getInt("id"),
                    name = item.getString("name"),
                    image = item.getString("image"),
                    birthDate = shortDate,
                    description = item.getString("description"),
                    albums = albums
                ))
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    fun getAlbumDetail(albumId:Int, onComplete:(resp:List<AlbumDetail>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums/$albumId",
            { response ->
                val resp = JSONObject(response)
                val list = mutableListOf<AlbumDetail>()
                val item: JSONObject = resp

                val tracksArray = item.getJSONArray("tracks")
                val tracks = mutableListOf<Track>()

                for (i in 0 until tracksArray.length()) {
                    val trackObject = tracksArray.getJSONObject(i)
                    val track = Track(
                        trackId = trackObject.getInt("id"),
                        name = trackObject.getString("name"),
                        duration = trackObject.getString("duration")
                    )
                    tracks.add(track)
                }

                val fechaCompleta = item.getString("releaseDate")
                val fecha = fechaCompleta.substring(0, 10)

                list.add(0, AlbumDetail(
                    albumId = item.getInt("id"),
                    name = item.getString("name"),
                    cover = item.getString("cover"),
                    recordLabel = item.getString("recordLabel"),
                    releaseDate = fecha,
                    genre = item.getString("genre"),
                    description = item.getString("description"),
                    tracks = tracks
                ))
                onComplete(list)
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