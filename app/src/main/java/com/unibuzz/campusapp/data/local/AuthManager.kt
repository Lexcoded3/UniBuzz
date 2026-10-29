package com.unibuzz.campusapp.data.local

import android.content.Context
import android.content.SharedPreferences
import com.unibuzz.campusapp.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthManager private constructor(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("unibuzz_auth_prefs", Context.MODE_PRIVATE)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun initialize(database: AppDatabase) {
        val savedUserId = prefs.getLong(KEY_USER_ID, -1L)
        if (savedUserId != -1L) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = database.userDao().getById(savedUserId)
                _currentUser.value = user
            }
        }
    }

    fun login(user: User) {
        prefs.edit().putLong(KEY_USER_ID, user.id).apply()
        _currentUser.value = user
    }

    fun logout() {
        prefs.edit().remove(KEY_USER_ID).apply()
        _currentUser.value = null
    }

    companion object {
        private const val KEY_USER_ID = "active_user_id"

        @Volatile
        private var INSTANCE: AuthManager? = null

        fun getInstance(context: Context): AuthManager {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthManager(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
