package com.unibuzz.campusapp.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ThemeMode {
    DARK,
    LIGHT,
    SYSTEM
}

enum class AppFontFamily(val label: String) {
    SANS_SERIF("Modern Sans"),
    SERIF("Classic Serif"),
    MONOSPACE("Tech Mono"),
    CURSIVE("Playful Cursive"),
}

class ThemeManager private constructor(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("unibuzz_theme_prefs", Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(getSavedThemeMode())
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _appFontFamily = MutableStateFlow(getSavedFontFamily())
    val appFontFamily: StateFlow<AppFontFamily> = _appFontFamily.asStateFlow()

    private fun getSavedThemeMode(): ThemeMode {
        val saved = prefs.getString(KEY_THEME_MODE, ThemeMode.DARK.name)
        return try {
            ThemeMode.valueOf(saved ?: ThemeMode.DARK.name)
        } catch (e: Exception) {
            ThemeMode.DARK
        }
    }

    private fun getSavedFontFamily(): AppFontFamily {
        val saved = prefs.getString(KEY_FONT_FAMILY, AppFontFamily.SANS_SERIF.name)
        return try {
            AppFontFamily.valueOf(saved ?: AppFontFamily.SANS_SERIF.name)
        } catch (e: Exception) {
            AppFontFamily.SANS_SERIF
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
        _themeMode.value = mode
    }

    fun setFontFamily(font: AppFontFamily) {
        prefs.edit().putString(KEY_FONT_FAMILY, font.name).apply()
        _appFontFamily.value = font
    }

    fun toggleTheme() {
        val nextMode = when (_themeMode.value) {
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
        }
        setThemeMode(nextMode)
    }

    companion object {
        private const val KEY_THEME_MODE = "theme_mode_key"
        private const val KEY_FONT_FAMILY = "font_family_key"

        @Volatile
        private var INSTANCE: ThemeManager? = null

        fun getInstance(context: Context): ThemeManager {
            return INSTANCE ?: synchronized(this) {
                val instance = ThemeManager(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
