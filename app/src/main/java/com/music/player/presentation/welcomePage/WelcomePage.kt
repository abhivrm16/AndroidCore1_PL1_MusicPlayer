package com.music.player.presentation.welcomePage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music.player.R
import com.music.player.data.model.FavouriteModel
import com.music.player.domain.use_case.welcomeMusicList.WelcomePageViewModel
import com.music.player.presentation.components.MusicAdapter
import com.music.player.presentation.savedPlayList.SavedPlayList

//Activity for  Welcome Page
@Suppress("KotlinConstantConditions")
class WelcomePage : AppCompatActivity() {
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var musicTracks: List<FavouriteModel>
    private lateinit var viewModel: WelcomePageViewModel

    companion object {
        private const val STORAGE_PERMISSION_REQUEST_CODE = 1001
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)

        supportActionBar?.title="Library"
        supportActionBar?.setIcon(R.drawable.menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this,R.drawable.baseline_menu_24))

        if (checkStoragePermission()) {
            displayAudioFiles()
        } else {
            requestStoragePermission()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_welcomepage,menu)
        var menuItem=menu!!.findItem(R.id.searchitem)
        var searchView: SearchView =menuItem.actionView as SearchView
        val searchIcon =
            searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)
        searchView.maxWidth= Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                musicAdapter.filter(newText.toString())
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite->{
                item.setIcon(R.drawable.favorite);
                val intent= Intent(this, SavedPlayList::class.java)
                startActivity(intent)

            }

        }
        return super.onOptionsItemSelected(item)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayAudioFiles()
            } else {
                Toast.makeText(
                    this,
                    "Permission denied. Cannot access audio files without storage permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun displayAudioFiles() {
        viewModel= ViewModelProvider(this).get(WelcomePageViewModel::class.java)
        musicTracks = viewModel.getMusicTrack(this)

        val recyclerViewMusic = findViewById<RecyclerView>(R.id.reclycleviewmusic)
        musicAdapter = MusicAdapter(musicTracks)
        recyclerViewMusic.adapter = musicAdapter
        recyclerViewMusic.layoutManager = LinearLayoutManager(this)

    }

}