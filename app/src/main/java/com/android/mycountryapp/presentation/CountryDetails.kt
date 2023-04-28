package com.android.mycountryapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.mycountryapp.data.models.Country
import com.android.mycountryapp.ui.theme.MyGreen
import com.android.mycountryapp.utils.Screen
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetails(
    country : Country?,
    navController: NavController
){
    if(country != null){
        Scaffold(
                topBar = { TopNavBar(navController) },

                content = { innerPadding ->

                          LazyColumn(
                                  modifier = Modifier
                                      .padding(innerPadding),
                                  verticalArrangement = Arrangement.Center,
                                  horizontalAlignment = Alignment.CenterHorizontally
                          ) {

                              item {
                                  Row(
                                          modifier = Modifier
                                              .fillMaxWidth()
                                              .height(100.dp)
                                              .padding(
                                                      vertical = 8.dp ,
                                                      horizontal = 4.dp
                                              ) ,
                                          verticalAlignment = Alignment.CenterVertically ,
                                          horizontalArrangement = Arrangement.Center
                                  ) {
                                      Image(
                                              painter = rememberAsyncImagePainter(model = country.coatOfArms.png) ,
                                              contentDescription = "" ,
                                              contentScale = ContentScale.Crop
                                      )
                                  }
                              }

                              item {
                                  Column(
                                          modifier = Modifier
                                              .fillMaxWidth()
                                              .padding(
                                                      vertical = 8.dp ,
                                                      horizontal = 4.dp
                                              ) ,
                                          horizontalAlignment = Alignment.CenterHorizontally ,
                                          verticalArrangement = Arrangement.Center
                                  ) {

                                      Text(
                                              country.name.official ,
                                              style = MaterialTheme.typography.headlineSmall.copy(
                                                      fontSize = 20.sp
                                              )
                                      )

                                      Text(
                                              country.name.common ,
                                              style = MaterialTheme.typography.bodyMedium.copy(
                                                      fontSize = 20.sp ,
                                                      fontStyle = FontStyle.Italic ,
                                                      fontWeight = FontWeight.W300
                                              )
                                      )

                                  }
                              }

                              item {
                                  Image(
                                          painter = rememberAsyncImagePainter(model = country.flags.png) ,
                                          contentDescription = "" ,
                                          modifier = Modifier
                                              .fillMaxWidth()
                                              .height(160.dp)
                                              .padding(vertical = 8.dp)
                                              .clip(
                                                      RoundedCornerShape(10)
                                              )
                                  )
                              }

                              item {
                                  Column(
                                          modifier = Modifier
                                              .fillMaxWidth()
                                              .padding(8.dp)
                                  ) {

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "Region :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )

                                          val region = country.region.uppercase()
                                          val printedRegion = if (!region.isNullOrEmpty()) region else "-"

                                          val subregion = country.subregion
                                          val printedSubregion = if (!subregion.isNullOrEmpty()) subregion else "-"


                                          Text(
                                                  text = "$printedRegion / $printedSubregion" ,
                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                          fontWeight = FontWeight.W500
                                                  ) ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "Area :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )

                                          val df = DecimalFormat("#,##0", DecimalFormatSymbols.getInstance()) // formateur de nombre
                                          val formattedArea = df.format(country.area)

                                          Text(
                                                  text = "$formattedArea m²" ,
                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                          fontWeight = FontWeight.W500
                                                  ) ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "Population :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )

                                          val df = DecimalFormat("#,##0", DecimalFormatSymbols.getInstance()) // formateur de nombre
                                          val formattedPopulation = df.format(country.population)

                                          Text(
                                                  text = formattedPopulation ,
                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                          fontWeight = FontWeight.W500
                                                  ) ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "Phone code :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )

                                          val root = country.idd.root
                                          val suffixes = country.idd.suffixes

                                          val rootWithSuffixes = mutableListOf<String>()
                                          val roots = if (suffixes.isNullOrEmpty()){
                                              "-"
                                          } else if(suffixes.size == 1) {
                                              root + suffixes.joinToString("")
                                          } else {

                                              for (suffix in suffixes) {
                                                  rootWithSuffixes.add(root + suffix)
                                              }

                                              rootWithSuffixes.joinToString(", ")
                                          }

                                          Text(
                                                  text = roots ,
                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                          fontWeight = FontWeight.W500
                                                  ) ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "ISO code :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )

                                          Text(
                                                  text = country.cca2 ,
                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                          fontWeight = FontWeight.W500
                                                  ) ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "Capital :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )

                                          val capitals = country.capital
                                          val printedCapitals = if (capitals.isNullOrEmpty()) "-"
                                          else if (capitals.size == 1) capitals.joinToString("")
                                          else capitals.joinToString(", ")

                                          Text(
                                                  text = printedCapitals,
                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                          fontWeight = FontWeight.W500
                                                  ) ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "languages :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )

                                          val languages = country.languages?.entries?.map {
                                              it.value
                                          }

                                          LazyRow(
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          ) {

                                              if (!languages.isNullOrEmpty()){

                                                  items(languages) { language ->

                                                      Row (modifier = Modifier.padding(end = 4.dp)){
                                                          Text(
                                                                  text = language ,
                                                                  color = Color.White ,
                                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                                          fontWeight = FontWeight.W500
                                                                  ) ,
                                                                  modifier = Modifier
                                                                      .clip(
                                                                              RoundedCornerShape(
                                                                                      topStart = 10.dp ,
                                                                                      topEnd = 10.dp ,
                                                                                      bottomStart = 10.dp ,
                                                                                      bottomEnd = 10.dp
                                                                              )
                                                                      )
                                                                      .background(MyGreen)
                                                                      .padding(2.dp)
                                                          )
                                                      }


                                                  }
                                              } else {
                                                  item { Text("-") }
                                              }

                                          }
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "Currencies :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )

                                          val currencyKey = country.currencies?.entries
                                              ?.map { it.key }

                                          val currencySymbol = country.currencies?.entries
                                              ?.map { it.value }
                                              ?.map { it.symbol }

                                          if (
                                              currencyKey.isNullOrEmpty() &&
                                              currencySymbol.isNullOrEmpty()
                                          ) {
                                              Text("-")
                                          } else {
                                              Text(
                                                      text = "${currencyKey?.joinToString("")} (${
                                                          currencySymbol?.joinToString(
                                                                  ""
                                                          )
                                                      })" ,
                                                      style = MaterialTheme.typography.bodyMedium.copy(
                                                              fontWeight = FontWeight.W500
                                                      ) ,
                                                      modifier = Modifier
                                                          .weight(0.5f)
                                              )
                                          }
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "Latitude :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )


                                          Text(
                                                  text = "${country.latlng[0]}" ,
                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                          fontWeight = FontWeight.W500
                                                  ) ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )
                                      }

                                      Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .padding(4.dp)
                                      ) {
                                          Text(
                                                  text = "Longitude :" ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )


                                          Text(
                                                  text = "${country.latlng[1]}" ,
                                                  style = MaterialTheme.typography.bodyMedium.copy(
                                                          fontWeight = FontWeight.W500
                                                  ) ,
                                                  modifier = Modifier
                                                      .weight(0.5f)
                                          )
                                      }

                                  }
                              }

                          }
                },

                bottomBar = {  }
        )
    }
}

@Composable
fun TopNavBar(
    navController: NavController
) {
    Column(modifier = Modifier.background(Color.White)) {

        Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                            vertical = 8.dp ,
                            horizontal = 4.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                    onClick = { navController.navigate(Screen.CountriesList.route) } ,
                    modifier = Modifier
                        .weight(0.15f)
            ) {
                Icon(
                        Icons.Rounded.ArrowBack ,
                        contentDescription = "Menu" ,
                        modifier = Modifier
                            .size(24.dp)
                )
            }

            Text(
                    "Country Details" ,
                    style = MaterialTheme.typography.headlineSmall ,
                    modifier = Modifier
                        .weight(0.65f)
            )

            IconButton(
                    onClick = { } ,
                    modifier = Modifier
                        .weight(0.15f)
            ) {
                Icon(
                        Icons.Rounded.Share ,
                        contentDescription = "Menu" ,
                        modifier = Modifier
                            .size(24.dp)
                )
            }

            IconButton(
                    onClick = { } ,
                    modifier = Modifier
                        .weight(0.15f)
            ) {
                Icon(
                        Icons.Rounded.MoreVert ,
                        contentDescription = "Menu" ,
                        modifier = Modifier
                            .size(24.dp)
                )
            }

        }

        Divider(
                Modifier.fillMaxWidth() ,
                color = Color.Black
        )
    }
}