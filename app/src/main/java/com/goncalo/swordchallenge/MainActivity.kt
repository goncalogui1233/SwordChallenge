package com.goncalo.swordchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goncalo.swordchallenge.presentation.bottombar.BottomNavigationBarUI
import com.goncalo.swordchallenge.presentation.catlist.screen.CatListScreen
import com.goncalo.swordchallenge.presentation.catlist.viewmodel.CatListViewModel
import com.goncalo.swordchallenge.presentation.common.ScreenCatList
import com.goncalo.swordchallenge.ui.theme.SwordChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwordChallengeTheme {
                val viewModel : CatListViewModel by viewModels()
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    BottomNavigationBarUI(
                        navController = navController
                    )
                }) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NavHost(
                            navController = navController, startDestination = ScreenCatList
                        ) {
                            composable<ScreenCatList> {
                                CatListScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SwordChallengeTheme {

    }
}