package com.litecodez.lawresources

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@UnstableApi
object VideoViewModel: ViewModel() {
    private val exoPlayer = mutableStateOf<ExoPlayer?>(null)

    fun initializePlayer(context: Context) {
        exoPlayer.value = ExoPlayer.Builder(context).build()
    }

    fun releasePlayer() {
        exoPlayer.value?.playWhenReady = false
        exoPlayer.value?.release()
        exoPlayer.value = null
    }

    fun playVideo(uri:String) {
        exoPlayer.value?.let { player ->
            player.apply {
                stop()
                clearMediaItems()
                setMediaItem(MediaItem.fromUri(Uri.parse(uri)))
                playWhenReady = true
                prepare()
                play()
            }
        }
    }

    fun playerViewBuilder(context: Context,callback:(PlayerView)->Unit={}): PlayerView {
        val activity = context as Activity
        val playerView = PlayerView(context).apply {
            player = exoPlayer.value
            controllerAutoShow = true
            keepScreenOn = true
            setFullscreenButtonClickListener { isFullScreen ->
                if (isFullScreen){
                    activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
                }else{
                    activity.requestedOrientation = SCREEN_ORIENTATION_USER
                }
            }
        }
        callback(playerView)
        return playerView
    }
}