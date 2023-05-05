package com.example.vinyls.model

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.model.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application){
    fun refreshData(callback: (List<AlbumDBDao>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }

    fun addTrackToAlbum(albumId:Int,body:JSONObject, callback: (Any)->Unit, onError: (VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).postTracksToAlbum(albumId,{
                callback(it)
            },
            onError,
            body
        )
    }
    fun createAlbum(callback: (AlbumDBDao)->Unit, onError: (VolleyError)->Unit, body: JSONObject) {
        NetworkServiceAdapter.getInstance(application).postAlbum({
            callback(it)
        },
            onError,
            body
        )
    }
}