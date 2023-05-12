package com.example.vinyls.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.ArtistDetailItemBinding
import com.squareup.picasso.Picasso


class ArtistDetailAdapter : RecyclerView.Adapter<ArtistDetailAdapter.ArtistDetailViewHolder>(){
    var artist :List<ArtistDetail> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistDetailViewHolder {
        val withDataBinding: ArtistDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistDetailViewHolder.LAYOUT,
            parent,
            false)
        return ArtistDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artistDetail = artist[position]
            Picasso.get().load(artist[position].image).resize(450, 450)
                .into(it.imageView)
            it.imageView.clipToOutline = true


            val tracksRecyclerView = it.tracksRecyclerView
            val trackAdapter = AlbumsAdapter()

            tracksRecyclerView.adapter = trackAdapter

            val albums = artist[position].albums
            trackAdapter.albums = albums

            tracksRecyclerView.layoutManager = LinearLayoutManager(it.root.context, LinearLayoutManager.HORIZONTAL, false)
            trackAdapter.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return artist.size
    }

    class ArtistDetailViewHolder(val viewDataBinding: ArtistDetailItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_detail_item
        }
    }
}