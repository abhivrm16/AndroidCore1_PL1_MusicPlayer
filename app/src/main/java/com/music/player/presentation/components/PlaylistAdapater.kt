package com.music.player.presentation.components

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.music.player.R

import com.music.player.data.model.FavouriteModel
import com.music.player.presentation.musicPlayer.MusicPlayer

class PlaylistAdapater(private val list: List<FavouriteModel>) : RecyclerView.Adapter< PlaylistAdapater.PlaylistViewHolder>() {
    private lateinit var mContext: Context
    private var filteredMusicList: List<FavouriteModel> = list

    class PlaylistViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val musicTitleName =view.findViewById<TextView>(R.id.playlist)
        val musicArtist=view.findViewById<TextView>(R.id.artist)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        mContext=parent.context
        return PlaylistViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.play_list_tiem, parent, false))
    }

    override fun getItemCount(): Int {
        return filteredMusicList.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val musicListName=filteredMusicList[position]
        holder.musicTitleName.text=musicListName.title
        holder.musicArtist.text=musicListName.artist
        holder.itemView.setOnClickListener {
            val intent= Intent(mContext, MusicPlayer::class.java)
            intent.putExtra("Music_list",musicListName)
            mContext.startActivity(intent)
        }

    }
}