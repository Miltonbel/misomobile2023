package com.example.vinyls.model

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.model.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<AlbumDBDao> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
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