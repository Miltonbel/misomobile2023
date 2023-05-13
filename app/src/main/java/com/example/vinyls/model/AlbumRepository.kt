package com.example.vinyls.model

import android.app.Application
import com.example.vinyls.model.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<AlbumDBDao> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun createAlbum(body: JSONObject): AlbumDBDao {
        return NetworkServiceAdapter.getInstance(application).postAlbum(body)
    }
}