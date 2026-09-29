package com.example.data.game.db

import android.content.Context
import android.content.SharedPreferences

class LocalGameStorage(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("memory_game_local_storage", Context.MODE_PRIVATE)

    fun getActivePlayerId(): String? {
        return prefs.getString(KEY_ACTIVE_PLAYER_ID, null)
    }

    fun setActivePlayerId(playerId: String) {
        prefs.edit().putString(KEY_ACTIVE_PLAYER_ID, playerId).apply()
    }

    fun clearActivePlayerId() {
        prefs.edit().remove(KEY_ACTIVE_PLAYER_ID).apply()
    }

    companion object {
        private const val KEY_ACTIVE_PLAYER_ID = "key_active_player_id"

        @Volatile
        private var INSTANCE: LocalGameStorage? = null

        fun getInstance(context: Context): LocalGameStorage {
            return INSTANCE ?: synchronized(this) {
                val instance = LocalGameStorage(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
