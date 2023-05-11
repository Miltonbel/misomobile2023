package com.example.vinyls.model

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.model.network.NetworkServiceAdapter

class AlbumDetailRepository (val application: Application){
    fun refreshData(albumId: Int, callback: (List<AlbumDetail>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbumDetail(albumId,{
            callback(it)
        },
            onError
        )
    }
}