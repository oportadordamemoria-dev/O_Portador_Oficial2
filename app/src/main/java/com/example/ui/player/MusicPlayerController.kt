package com.example.ui.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.data.BookMusicLibrary
import com.example.data.MusicTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MusicPlayerController(private val context: Context) {
    var playlist by mutableStateOf<List<MusicTrack>>(BookMusicLibrary.tracks)
        private set

    var currentTrackIndex by mutableIntStateOf(0)
        private set

    val currentTrack: MusicTrack?
        get() = playlist.getOrNull(currentTrackIndex)

    var isPlaying by mutableStateOf(false)
        private set

    var currentPositionMs by mutableLongStateOf(0L)
        private set

    var durationMs by mutableLongStateOf(BookMusicLibrary.tracks.first().durationMs)
        private set

    var isShuffle by mutableStateOf(false)
        private set

    var isRepeatOne by mutableStateOf(false)
        private set

    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    private var progressJob: Job? = null

    init {
        // Prepare first track duration
        currentTrack?.let { durationMs = it.durationMs }
    }

    fun playTrackAt(index: Int) {
        if (index !in playlist.indices) return
        currentTrackIndex = index
        val track = playlist[index]
        startPlayback(track)
    }

    fun togglePlayPause() {
        if (isPlaying) {
            pause()
        } else {
            if (mediaPlayer == null) {
                currentTrack?.let { startPlayback(it) }
            } else {
                resume()
            }
        }
    }

    private fun startPlayback(track: MusicTrack) {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null

            val rawId = track.rawResName?.let { name ->
                val id = context.resources.getIdentifier(name, "raw", context.packageName)
                if (id != 0) id else null
            }

            val player = when {
                track.customUri != null -> MediaPlayer.create(context, track.customUri)
                rawId != null -> MediaPlayer.create(context, rawId)
                else -> null
            }

            if (player != null) {
                mediaPlayer = player
                player.isLooping = isRepeatOne
                player.setOnCompletionListener {
                    if (isRepeatOne) {
                        player.start()
                    } else {
                        nextTrack()
                    }
                }
                player.start()
                isPlaying = true
                val realDuration = player.duration.toLong()
                durationMs = if (realDuration > 0) realDuration else track.durationMs
                startProgressTracker()
            } else {
                // Fallback simulation state if resource load issue
                isPlaying = true
                durationMs = track.durationMs
                startProgressTracker()
            }
        } catch (e: Exception) {
            Log.e("MusicPlayerController", "Error starting playback: ${e.message}", e)
        }
    }

    fun pause() {
        try {
            mediaPlayer?.pause()
        } catch (e: Exception) {
            Log.e("MusicPlayerController", "Error pausing: ${e.message}", e)
        }
        isPlaying = false
        stopProgressTracker()
    }

    fun resume() {
        try {
            mediaPlayer?.start()
            isPlaying = true
            startProgressTracker()
        } catch (e: Exception) {
            currentTrack?.let { startPlayback(it) }
        }
    }

    fun stop() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            // Re-create on next play
            mediaPlayer?.release()
            mediaPlayer = null
        }
        isPlaying = false
        currentPositionMs = 0L
        stopProgressTracker()
    }

    fun nextTrack() {
        if (playlist.isEmpty()) return
        val nextIdx = if (isShuffle) {
            playlist.indices.filter { it != currentTrackIndex }.randomOrNull() ?: currentTrackIndex
        } else {
            (currentTrackIndex + 1) % playlist.size
        }
        playTrackAt(nextIdx)
    }

    fun previousTrack() {
        if (playlist.isEmpty()) return
        if (currentPositionMs > 3000L) {
            seekTo(0L)
            return
        }
        val prevIdx = if (currentTrackIndex - 1 < 0) playlist.size - 1 else currentTrackIndex - 1
        playTrackAt(prevIdx)
    }

    fun seekTo(positionMs: Long) {
        val bounded = positionMs.coerceIn(0L, durationMs)
        currentPositionMs = bounded
        try {
            mediaPlayer?.seekTo(bounded.toInt())
        } catch (e: Exception) {
            Log.e("MusicPlayerController", "Error seeking: ${e.message}", e)
        }
    }

    fun toggleShuffle() {
        isShuffle = !isShuffle
    }

    fun toggleRepeat() {
        isRepeatOne = !isRepeatOne
        mediaPlayer?.isLooping = isRepeatOne
    }

    fun addCustomTrack(uri: Uri, displayName: String) {
        val newTrack = MusicTrack(
            id = "custom_${System.currentTimeMillis()}",
            title = displayName.substringBeforeLast(".").ifBlank { "Música Importada" },
            subtitle = "Faixa importada pelo usuário",
            actConnection = "Arquivo Personalizado",
            durationFormatted = "--:--",
            durationMs = 180000L,
            description = "Áudio importado localmente pelo usuário do dispositivo.",
            mood = "Personalizado",
            source = "Arquivo Local (.mp3/.wav)",
            customUri = uri
        )
        playlist = playlist + newTrack
        playTrackAt(playlist.size - 1)
    }

    private fun startProgressTracker() {
        stopProgressTracker()
        progressJob = scope.launch {
            while (isActive && isPlaying) {
                try {
                    val pos = mediaPlayer?.currentPosition?.toLong()
                    if (pos != null && pos >= 0) {
                        currentPositionMs = pos
                    } else {
                        currentPositionMs = (currentPositionMs + 250L).coerceAtMost(durationMs)
                    }
                } catch (e: Exception) {
                    currentPositionMs = (currentPositionMs + 250L).coerceAtMost(durationMs)
                }
                delay(250L)
            }
        }
    }

    private fun stopProgressTracker() {
        progressJob?.cancel()
        progressJob = null
    }

    fun release() {
        stopProgressTracker()
        try {
            mediaPlayer?.release()
        } catch (e: Exception) {
            // Ignored
        }
        mediaPlayer = null
        isPlaying = false
    }
}
