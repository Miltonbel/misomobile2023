package com.example.vinyls.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinyls.model.database.AlbumsDao
import com.example.vinyls.model.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application, private val albumsDao: AlbumsDao){

    suspend fun refreshData(): List<Album> {
        var cached = albumsDao.getAlbums()
        return if(cached.isNullOrEmpty()){
            Log.d("Cache decision", "get from network")
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {

                val tmpListAlbums = NetworkServiceAdapter.getInstance(application).getAlbums()
                for (album in tmpListAlbums) {
                    albumsDao.insert(album)
                }
                return tmpListAlbums
            }
        } else{
            Log.d("Cache decision", "return ${cached.size} elements from cache")
            return cached
        }
    }

    suspend fun createAlbum(body: JSONObject): Album {
        var newAlbum = NetworkServiceAdapter.getInstance(application).postAlbum(body)
        albumsDao.insert(newAlbum)
        return newAlbum
    }

    suspend fun addTrackToAlbum(albumId:Int,body:JSONObject){
        NetworkServiceAdapter.getInstance(application).postTracksToAlbum(albumId, body)
    }

}