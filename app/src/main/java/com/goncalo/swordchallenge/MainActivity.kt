package com.goncalo.swordchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.goncalo.domain.usecase.SetAppStartupFlagUseCase
import com.goncalo.presentation.bottombar.BottomNavigationBarUI
import com.goncalo.presentation.catdetail.screen.CatDetailScreen
import com.goncalo.presentation.catdetail.viewmodel.CatDetailViewModel
import com.goncalo.presentation.catlist.screen.CatFavouriteScreen
import com.goncalo.presentation.catlist.screen.CatListScreen
import com.goncalo.presentation.catlist.viewmodel.CatListViewModel
import com.goncalo.presentation.common.helpers.CatDetailScreen
import com.goncalo.presentation.common.helpers.ScreenCatFavourite
import com.goncalo.presentation.common.helpers.ScreenCatList
import com.goncalo.swordchallenge.ui.theme.SwordChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var setAppStartupFlagUseCase: SetAppStartupFlagUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //If null, app is being launched for the first time, so reset app startup flag to force new load on ScreenCatList
        if(savedInstanceState == null) {
            lifecycleScope.launch {
                setAppStartupFlagUseCase(true)
            }
        }

        enableEdgeToEdge()
        setContent {
            SwordChallengeTheme {
                val catListViewModel : CatListViewModel by viewModels()
                val catDetailViewModel : CatDetailViewModel by viewModels()
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
                            navController = navController,
                            startDestination = ScreenCatList
                        ) {
                            composable<ScreenCatList> {
                                CatListScreen(viewModel = catListViewModel, navController = navController)
                            }

                            composable<ScreenCatFavourite> {
                                CatFavouriteScreen(viewModel = catListViewModel, navController = navController)
                            }

                            composable<CatDetailScreen> {
                                val catId = it.toRoute<CatDetailScreen>().catId
                                val detailSource = it.toRoute<CatDetailScreen>().detailSource
                                CatDetailScreen(
                                    viewModel = catDetailViewModel,
                                    catId = catId,
                                    catDetailRequestSource = detailSource,
                                    navController = navController
                                )
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