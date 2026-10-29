package com.unibuzz.campusapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unibuzz.campusapp.data.local.AppDatabase
import com.unibuzz.campusapp.data.local.ThemeManager
import com.unibuzz.campusapp.ui.App
import com.unibuzz.campusapp.ui.theme.UnibuzzTheme
import kotlinx.coroutines.delay

@Composable
fun UnibuzzSplashLoading() {
    val infiniteTransition = rememberInfiniteTransition(label = "dotsTransition")

    val dot1Scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "dot1",
    )

    val dot2Scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 200, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "dot2",
    )

    val dot3Scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 400, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "dot3",
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(80.dp),
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = "Unibuzz Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "UNIBUZZ",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp,
                ),
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val primaryColor = MaterialTheme.colorScheme.primary
                val dots = listOf(
                    dot1Scale to primaryColor,
                    dot2Scale to primaryColor.copy(alpha = 0.8f),
                    dot3Scale to primaryColor.copy(alpha = 0.6f),
                )
                dots.forEach { (scale, color) ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .scale(scale)
                            .background(
                                color = color.copy(alpha = (scale * 0.7f + 0.3f).coerceIn(0.3f, 1.0f)),
                                shape = CircleShape,
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun AppScreen(
    database: AppDatabase,
) {
    val context = LocalContext.current
    val themeManager = remember(context) { ThemeManager.getInstance(context) }
    val themeMode by themeManager.themeMode.collectAsState()
    val appFontFamily by themeManager.appFontFamily.collectAsState()

    var showMainContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(600)
        showMainContent = true
    }

    UnibuzzTheme(themeMode = themeMode, fontFamily = appFontFamily) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (!showMainContent) {
                UnibuzzSplashLoading()
            } else {
                App(database = database)
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val app = application as Application
            val database = app.database

            AppScreen(database = database)
        }
    }
}
