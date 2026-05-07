package com.example.a211390_ganthaithie_nelson_lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a211390_ganthaithie_nelson_lab1.ui.theme.AppTheme
import com.example.a211390_ganthaithie_nelson_lab1.navigation.AppNavigation
import com.example.a211390_ganthaithie_nelson_lab1.viewmodel.AppViewModel

/**
 * MainActivity - Entry point of the EcoLoop Application
 * 
 * This activity sets up the app with:
 * - Edge-to-edge display support
 * - Custom Material Design theme (AppTheme)
 * - Jetpack Navigation Compose for screen management
 * - ViewModel for centralized data management
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(dynamicColor = false) {
                EcoLoopNavigationApp()
            }
        }
    }
}

/**
 * Main app composable using Jetpack Navigation Compose
 * 
 * This replaces manual state-based navigation with a structured navigation graph.
 * Benefits:
 * - Type-safe navigation with sealed classes
 * - Automatic back stack management
 * - Better screen state preservation
 * - Centralized navigation logic
 */
@Composable
fun EcoLoopNavigationApp(
    viewModel: AppViewModel = viewModel()
) {
    val navController = rememberNavController()
    
    AppNavigation(
        navController = navController,
        viewModel = viewModel
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppTheme {
        EcoLoopNavigationApp()
    }
}
