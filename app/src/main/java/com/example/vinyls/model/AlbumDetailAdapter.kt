package com.example.vinyls.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinyls.R
import com.example.vinyls.databinding.AlbumDetailItemBinding


class AlbumDetailAdapter : RecyclerView.Adapter<AlbumDetailAdapter.AlbumDetailViewHolder>(){
    var album :List<AlbumDetail> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailViewHolder {
        val withDataBinding: AlbumDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumDetailViewHolder.LAYOUT,
            parent,
            false)
        return AlbumDetailViewHolder(withDataBinding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: AlbumDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albumDetail = album[position]
            holder.bind(album[position])
            it.imageView.clipToOutline = true

            val tracksRecyclerView = it.tracksRecyclerView
            val trackAdapter = TrackAdapter()

            tracksRecyclerView.adapter = trackAdapter

            val tracks = album[position].tracks
            trackAdapter.tracks = tracks

            tracksRecyclerView.layoutManager = LinearLayoutManager(it.root.context, LinearLayoutManager.HORIZONTAL, false)
            trackAdapter.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return album.size
    }

    class AlbumDetailViewHolder(val viewDataBinding: AlbumDetailItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_detail_item
        }
        fun bind(album: AlbumDetail) {
            Glide.with(itemView)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .override(450, 450)
                        .error(R.drawable.ic_broken_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewDataBinding.imageView)
        }
    }
}