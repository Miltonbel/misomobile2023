package com.example.vinyls.model

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.model.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application){

    suspend fun refreshData(): List<AlbumDBDao> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun createAlbum(body: JSONObject): AlbumDBDao {
        return NetworkServiceAdapter.getInstance(application).postAlbum(body)
    }

    suspend fun addTrackToAlbum(albumId:Int,body:JSONObject){
        NetworkServiceAdapter.getInstance(application).postTracksToAlbum(albumId, body)
    }

}