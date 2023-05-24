package com.example.vinyls.model

data class ArtistDetail(
    val artistId:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String,
    val albums:List<Album>
)