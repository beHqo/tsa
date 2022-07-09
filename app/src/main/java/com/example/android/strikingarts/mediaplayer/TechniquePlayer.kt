package com.example.android.strikingarts.mediaplayer

import android.content.Context
import com.example.android.strikingarts.database.entity.Technique

class TechniquePlayer(context: Context,private var techniqueList: List<Technique>) {
    private val mp = MediaPlayerWrapper(context, techniqueList)

    fun play() {
        mp.initializeMediaPlayers()
    }

    fun pause() {
        mp.pause()
    }

    fun release() {
        mp.destroyAllMediaPlayers()
    }
}