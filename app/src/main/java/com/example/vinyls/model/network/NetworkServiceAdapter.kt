package com.example.vinyls.model.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinyls.model.Album
import com.example.vinyls.model.AlbumDetail
import com.example.vinyls.model.Artist
import com.example.vinyls.model.ArtistDetail
import com.google.gson.Gson
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://10.0.2.2/"

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

    suspend fun getAlbums() = suspendCoroutine { cont->
        requestQueue.add(
            getRequest("albums",
                { response ->
                    val arrayAlbum: Array<Album> = Gson().fromJson(response, Array<Album>::class.java)
                    cont.resume(arrayAlbum.asList())
                },

                {
                    cont.resumeWithException(it)
                }))
    }

    suspend fun postAlbum(body: JSONObject) = suspendCoroutine<Album>{ cont->
        requestQueue.add(
            postRequest("albums",
                body,
                { response ->
                    val albumResponse = Gson().fromJson(response.toString(), Album::class.java)
                    cont.resume(albumResponse)
                }
            ) {
                cont.resumeWithException(it)
            })
    }

    suspend fun getArtists() = suspendCoroutine{ cont->
        requestQueue.add(
            getRequest("musicians",
                { response ->
                    val arrayArtist: Array<Artist> = Gson().fromJson(response, Array<Artist>::class.java)
                    cont.resume(arrayArtist.asList())
                },
                {
                    cont.resumeWithException(it)
                }))
    }

    suspend fun postArtist(body: JSONObject) = suspendCoroutine<Artist>{ cont->
        requestQueue.add(
            postRequest("musicians",
                body,
                { response ->
                    val artistResponse = Gson().fromJson(response.toString(), Artist::class.java)
                    cont.resume(artistResponse)
                },
                {
                    cont.resumeWithException(it)
                }))
    }

    suspend fun getArtistDetail(artistId:Int) = suspendCoroutine<List<ArtistDetail>>{ cont->
        requestQueue.add(getRequest("musicians/$artistId",
            { response ->
                val artistDetail = Gson().fromJson(response, ArtistDetail::class.java)
                cont.resume(listOf(artistDetail))
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun postTracksToAlbum(albumId:Int, body:JSONObject) = suspendCoroutine<Any> { cont ->
        requestQueue.add(
            postRequest(String.format("albums/%s/tracks", albumId), body,
                { response ->
                    val addedResponse = Gson().fromJson(response.toString(), Any::class.java)
                    cont.resume(addedResponse)
                }
            ) {
                cont.resumeWithException(it)
            }
        )
    }

    suspend fun postAlbumToArtist(albumId:Int, artistId:Int) = suspendCoroutine<Any> { cont ->
        requestQueue.add(
            postRequest(String.format("musicians/%s/albums/%s", artistId, albumId), null,
                { response ->
                    val addedResponse = Gson().fromJson(response.toString(), Any::class.java)
                    cont.resume(addedResponse)
                }
            ) {
                cont.resumeWithException(it)
            }
        )
    }

    suspend fun getAlbumDetail(albumId:Int) = suspendCoroutine<List<AlbumDetail>>{ cont->
        requestQueue.add(getRequest("albums/$albumId",
            { response ->
                val albumDetail = Gson().fromJson(response, AlbumDetail::class.java)
                cont.resume(listOf(albumDetail))
            },
            {
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(
        path: String,
        body: JSONObject?,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }
}