package com.unibuzz.campusapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Netflix Obsidian Palette & Accents
val NetflixRed = Color(0xFFE50914)             // Signature Accent Red
val NetflixRedDark = Color(0xFFB81D24)         // Darker Red
val ElectricViolet = Color(0xFF6366F1)         // Secondary Accent
val EmeraldGreen = Color(0xFF10B981)           // Success / Price Badge
val AmberOrange = Color(0xFFF59E0B)            // Warning / Tag Accent

// Obsidian Dark Background & Surfaces
val ObsidianBackground = Color(0xFF09090B)     // Ultra Dark Bg (#09090B)
val ObsidianSurface = Color(0xFF121215)        // Card / Surface Bg (#121215)
val ObsidianSurfaceVariant = Color(0xFF1E1E24) // Elevated Surface / Input (#1E1E24)
val ObsidianBorder = Color(0xFF27272A)         // Subtle Card Border

// Light Theme Palette (Studio Clean Light)
val LightBackground = Color(0xFFF8FAFC)        // Clean Light Slate Bg
val LightSurface = Color(0xFFFFFFFF)           // White Surface Card
val LightSurfaceVariant = Color(0xFFF1F5F9)    // Elevated Surface / Input
val LightBorder = Color(0xFFE2E8F0)            // Light Border
val TextPrimaryLight = Color(0xFF0F172A)       // High contrast text
val TextSecondaryLight = Color(0xFF64748B)     // Cool grey text

// Text & Content Shades
val TextPrimary = Color(0xFFFAFAFA)            // High contrast text
val TextSecondary = Color(0xFFA1A1AA)          // Muted text
val TextMuted = Color(0xFF71717A)              // Subtitle/Caption text

// Material 3 Color Schemes
val darkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = NetflixRed,
    onPrimary = Color.White,
    primaryContainer = NetflixRedDark,
    onPrimaryContainer = Color.White,
    secondary = ElectricViolet,
    onSecondary = Color.White,
    tertiary = EmeraldGreen,
    onTertiary = Color.White,
    background = ObsidianBackground,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = ObsidianBorder,
    inverseSurface = TextPrimary,
    inverseOnSurface = ObsidianBackground,
    error = Color(0xFFEF4444),
    onError = Color.White,
)

val lightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = NetflixRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFEE2E2),
    onPrimaryContainer = NetflixRedDark,
    secondary = ElectricViolet,
    onSecondary = Color.White,
    tertiary = EmeraldGreen,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = TextPrimaryLight,
    surface = LightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = TextSecondaryLight,
    outline = LightBorder,
    inverseSurface = ObsidianSurface,
    inverseOnSurface = TextPrimary,
    error = Color(0xFFEF4444),
    onError = Color.White,
)

// Reusable Gradients
@Composable
fun heroOverlayGradient(): Brush {
    val bg = MaterialTheme.colorScheme.background
    return Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            bg.copy(alpha = 0.5f),
            bg,
        ),
    )
}

val HeroOverlayGradient: Brush
    @Composable
    get() = heroOverlayGradient()

val PrimaryGradient = Brush.horizontalGradient(
    colors = listOf(NetflixRed, ElectricViolet),
)

val AccentGradient = Brush.linearGradient(
    colors = listOf(ElectricViolet, EmeraldGreen),
)

@Composable
fun glassBorderGradient(): Brush {
    val outlineColor = MaterialTheme.colorScheme.outline
    return Brush.linearGradient(
        colors = listOf(
            outlineColor,
            outlineColor.copy(alpha = 0.5f),
        ),
    )
}

val GlassBorderGradient: Brush
    @Composable
    get() = glassBorderGradient()
