package com.example.ecommercefoodappcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

//
// class ThemeState {
//    var currentAppTheme by mutableStateOf(AppTheme.RED)
// }
//
// val LocalThemeState = staticCompositionLocalOf<ThemeState> {
//    error("No ThemeState provided")
// }
//
// @Composable
// fun AppThemeWrapper(content: @Composable () -> Unit) {
//    val themeState = remember { ThemeState() }
//
//    CompositionLocalProvider(LocalThemeState provides themeState) {
//        content()
//    }
// }

enum class AppTheme {
    GREEN, RED
}

val GreenLightColorScheme = lightColorScheme(
    primary = primaryLightGreen,
    onPrimary = onPrimaryLightGreen,
    primaryContainer = primaryContainerLightGreen,
    onPrimaryContainer = onPrimaryContainerLightGreen,
    secondary = secondaryLightGreen,
    onSecondary = onSecondaryLightGreen,
    secondaryContainer = secondaryContainerLightGreen,
    onSecondaryContainer = onSecondaryContainerLightGreen,
    tertiary = tertiaryLightGreen,
    onTertiary = onTertiaryLightGreen,
    tertiaryContainer = tertiaryContainerLightGreen,
    onTertiaryContainer = onTertiaryContainerLightGreen,
    error = errorLightGreen,
    onError = onErrorLightGreen,
    errorContainer = errorContainerLightGreen,
    onErrorContainer = onErrorContainerLightGreen,
    background = backgroundLightGreen,
    onBackground = onBackgroundLightGreen,
    surface = surfaceLightGreen,
    onSurface = onSurfaceLightGreen,
    surfaceVariant = surfaceVariantLightGreen,
    onSurfaceVariant = onSurfaceVariantLightGreen,
    outline = outlineLightGreen,
    outlineVariant = outlineVariantLightGreen,
    scrim = scrimLightGreen,
    inverseSurface = inverseSurfaceLightGreen,
    inverseOnSurface = inverseOnSurfaceLightGreen,
    inversePrimary = inversePrimaryLightGreen,
    surfaceDim = surfaceDimLightGreen,
    surfaceBright = surfaceBrightLightGreen,
    surfaceContainerLowest = surfaceContainerLowestLightGreen,
    surfaceContainerLow = surfaceContainerLowLightGreen,
    surfaceContainer = surfaceContainerLightGreen,
    surfaceContainerHigh = surfaceContainerHighLightGreen,
    surfaceContainerHighest = surfaceContainerHighestLightGreen
)

val GreenDarkColorScheme = darkColorScheme(
    primary = primaryDarkGreen,
    onPrimary = onPrimaryDarkGreen,
    primaryContainer = primaryContainerDarkGreen,
    onPrimaryContainer = onPrimaryContainerDarkGreen,
    secondary = secondaryDarkGreen,
    onSecondary = onSecondaryDarkGreen,
    secondaryContainer = secondaryContainerDarkGreen,
    onSecondaryContainer = onSecondaryContainerDarkGreen,
    tertiary = tertiaryDarkGreen,
    onTertiary = onTertiaryDarkGreen,
    tertiaryContainer = tertiaryContainerDarkGreen,
    onTertiaryContainer = onTertiaryContainerDarkGreen,
    error = errorDarkGreen,
    onError = onErrorDarkGreen,
    errorContainer = errorContainerDarkGreen,
    onErrorContainer = onErrorContainerDarkGreen,
    background = backgroundDarkGreen,
    onBackground = onBackgroundDarkGreen,
    surface = surfaceDarkGreen,
    onSurface = onSurfaceDarkGreen,
    surfaceVariant = surfaceVariantDarkGreen,
    onSurfaceVariant = onSurfaceVariantDarkGreen,
    outline = outlineDarkGreen,
    outlineVariant = outlineVariantDarkGreen,
    scrim = scrimDarkGreen,
    inverseSurface = inverseSurfaceDarkGreen,
    inverseOnSurface = inverseOnSurfaceDarkGreen,
    inversePrimary = inversePrimaryDarkGreen,
    surfaceDim = surfaceDimDarkGreen,
    surfaceBright = surfaceBrightDarkGreen,
    surfaceContainerLowest = surfaceContainerLowestDarkGreen,
    surfaceContainerLow = surfaceContainerLowDarkGreen,
    surfaceContainer = surfaceContainerDarkGreen,
    surfaceContainerHigh = surfaceContainerHighDarkGreen,
    surfaceContainerHighest = surfaceContainerHighestDarkGreen
)

val RedLightColorScheme = lightColorScheme(
    primary = primaryLightRed,
    onPrimary = onPrimaryLightRed,
    primaryContainer = primaryContainerLightRed,
    onPrimaryContainer = onPrimaryContainerLightRed,
    secondary = secondaryLightRed,
    onSecondary = onSecondaryLightRed,
    secondaryContainer = secondaryContainerLightRed,
    onSecondaryContainer = onSecondaryContainerLightRed,
    tertiary = tertiaryLightRed,
    onTertiary = onTertiaryLightRed,
    tertiaryContainer = tertiaryContainerLightRed,
    onTertiaryContainer = onTertiaryContainerLightRed,
    error = errorLightRed,
    onError = onErrorLightRed,
    errorContainer = errorContainerLightRed,
    onErrorContainer = onErrorContainerLightRed,
    background = backgroundLightRed,
    onBackground = onBackgroundLightRed,
    surface = surfaceLightRed,
    onSurface = onSurfaceLightRed,
    surfaceVariant = surfaceVariantLightRed,
    onSurfaceVariant = onSurfaceVariantLightRed,
    outline = outlineLightRed,
    outlineVariant = outlineVariantLightRed,
    scrim = scrimLightRed,
    inverseSurface = inverseSurfaceLightRed,
    inverseOnSurface = inverseOnSurfaceLightRed,
    inversePrimary = inversePrimaryLightRed,
    surfaceDim = surfaceDimLightRed,
    surfaceBright = surfaceBrightLightRed,
    surfaceContainerLowest = surfaceContainerLowestLightRed,
    surfaceContainerLow = surfaceContainerLowLightRed,
    surfaceContainer = surfaceContainerLightRed,
    surfaceContainerHigh = surfaceContainerHighLightRed,
    surfaceContainerHighest = surfaceContainerHighestLightRed
)

val RedDarkColorScheme = darkColorScheme(
    primary = primaryDarkRed,
    onPrimary = onPrimaryDarkRed,
    primaryContainer = primaryContainerDarkRed,
    onPrimaryContainer = onPrimaryContainerDarkRed,
    secondary = secondaryDarkRed,
    onSecondary = onSecondaryDarkRed,
    secondaryContainer = secondaryContainerDarkRed,
    onSecondaryContainer = onSecondaryContainerDarkRed,
    tertiary = tertiaryDarkRed,
    onTertiary = onTertiaryDarkRed,
    tertiaryContainer = tertiaryContainerDarkRed,
    onTertiaryContainer = onTertiaryContainerDarkRed,
    error = errorDarkRed,
    onError = onErrorDarkRed,
    errorContainer = errorContainerDarkRed,
    onErrorContainer = onErrorContainerDarkRed,
    background = backgroundDarkRed,
    onBackground = onBackgroundDarkRed,
    surface = surfaceDarkRed,
    onSurface = onSurfaceDarkRed,
    surfaceVariant = surfaceVariantDarkRed,
    onSurfaceVariant = onSurfaceVariantDarkRed,
    outline = outlineDarkRed,
    outlineVariant = outlineVariantDarkRed,
    scrim = scrimDarkRed,
    inverseSurface = inverseSurfaceDarkRed,
    inverseOnSurface = inverseOnSurfaceDarkRed,
    inversePrimary = inversePrimaryDarkRed,
    surfaceDim = surfaceDimDarkRed,
    surfaceBright = surfaceBrightDarkRed,
    surfaceContainerLowest = surfaceContainerLowestDarkRed,
    surfaceContainerLow = surfaceContainerLowDarkRed,
    surfaceContainer = surfaceContainerDarkRed,
    surfaceContainerHigh = surfaceContainerHighDarkRed,
    surfaceContainerHighest = surfaceContainerHighestDarkRed
)

@Composable
fun ECommerceFoodAppComposeTheme(
    selectedTheme: AppTheme = AppTheme.GREEN,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when (selectedTheme) {
        AppTheme.RED -> if (useDarkTheme) RedDarkColorScheme else RedLightColorScheme
        AppTheme.GREEN -> if (useDarkTheme) GreenDarkColorScheme else GreenLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

// @Composable
// fun ECommerceFoodAppComposeTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
// ) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> darkScheme
//        else -> lightScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = AppTypography,
//        content = content
//    )
// }
