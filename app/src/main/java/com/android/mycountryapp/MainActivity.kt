package com.android.mycountryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.mycountryapp.presentation.CountriesList
import com.android.mycountryapp.presentation.CountryDetails
import com.android.mycountryapp.ui.theme.MyCountryAppTheme
import com.android.mycountryapp.utils.Screen
import com.android.mycountryapp.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCountryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize() ,
                        color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val countryModel: CountryViewModel = viewModel()

                    NavHost(
                            navController = navController ,
                            startDestination = Screen.CountriesList.route ,
                    ) {

                        countryModel.getAll()

                        composable(
                                route = Screen.CountriesList.route
                        ){
                            CountriesList(
                                    countriesState = countryModel.countriesState.collectAsState().value,
                                    navController = navController
                            )
                        }
                        composable(
                                route = "${Screen.CountryDetails.route}/{countryName}",
                                arguments = listOf(navArgument("countryName") { type = NavType.StringType })

                        ){ backStackEntry ->

                            countryModel.getByName(backStackEntry.arguments?.getString("countryName").toString())

                            CountryDetails(
                                    countryState = countryModel.countryState.collectAsState().value,
                                    navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCountryAppTheme {
        Greeting("Android")
    }
}