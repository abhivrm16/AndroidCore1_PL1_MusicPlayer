package com.music.player.domain.use_case.welcomeMusicList

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.music.player.data.model.FavouriteModel
import com.music.player.data.remote.MusicRoomDatabase
import com.music.player.data.repository.MainRepository

// ViewModel for getting Music List from mobile and  Connecting UseCase and Activity of Welcome Page

class WelcomePageViewModel (application: Application): AndroidViewModel(application) {
    private lateinit var repository: MainRepository
    init {
        val database= MusicRoomDatabase.getInstance(application.applicationContext)
        val dao=database.favouriteData()
        repository= MainRepository(dao)

    }

    fun getMusicTrack(context: Context): List<FavouriteModel> {
        return repository.getMusicTrack(context)
    }

}