package com.example.vinyls.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.vinyls.model.database.ArtistsDao
import com.example.vinyls.model.network.NetworkServiceAdapter
import org.json.JSONObject

class ArtistRepository (val application: Application, private val artistsDao: ArtistsDao){

    suspend fun refreshData(): List<Artist> {
        var cached = artistsDao.getArtists()
        return if(cached.isNullOrEmpty()){
            Log.d("Cache decision", "get from network")
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {

                val tmpListArtists = NetworkServiceAdapter.getInstance(application).getArtists()
                for (artist in tmpListArtists) {
                    artistsDao.insert(artist)
                }
                return tmpListArtists
            }
        } else{
            Log.d("Cache decision", "return ${cached.size} elements from cache")
            return cached
        }
    }

    suspend fun addAlbumToArtist(albumId:Int, artistId:Int){
        NetworkServiceAdapter.getInstance(application).postAlbumToArtist(albumId, artistId)
    }

    suspend fun createArtist(body: JSONObject): Artist {
        var newArtist = NetworkServiceAdapter.getInstance(application).postArtist(body)
        artistsDao.insert(newArtist)
        return newArtist
    }
}