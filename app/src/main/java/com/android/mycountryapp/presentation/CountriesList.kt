package com.android.mycountryapp.presentation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.mycountryapp.data.models.CountriesData
import com.android.mycountryapp.ui.theme.MyGreen
import com.android.mycountryapp.utils.Screen
import com.android.mycountryapp.viewmodel.CountryViewModel
import com.android.mycountryapp.viewmodel.states.CountryState

//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesList(
    countryModel: CountryViewModel,
    navController: NavController
) {

    val countriesState = countryModel.countriesState.collectAsState().value

    val pageSize: Int = (250 + 8) / 9
    var actualPage by remember { mutableStateOf(0) }

    Scaffold(

            topBar = {

                TopAppBar()
            },

            content = { innerPadding ->

                BodyList(
                        innerPadding ,
                        countryModel ,
                        countriesState ,
                        navController,
                        actualPage
                )
            },

            bottomBar = {
                Row(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                            onClick = { actualPage = 0 },
                            modifier = Modifier
                                .weight(1.5f),
                            enabled = actualPage != 0
                    ) {
                        Icon(
                                Icons.Rounded.SkipPrevious,
                                contentDescription = "",
                        )
                    }

                    IconButton(
                            onClick = { actualPage-- },
                            modifier = Modifier
                                .weight(1.5f),
                            enabled = actualPage != 0
                    ) {
                        Icon(
                                Icons.Rounded.KeyboardArrowLeft,
                                contentDescription = ""
                        )
                    }

                    val page = if (actualPage < 9) "0${actualPage + 1}" else "${actualPage + 1}"

                    Row(
                            modifier = Modifier.weight(4f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                                "Page $page sur $pageSize"
                        )
                    }

                    IconButton(
                            onClick = { actualPage++ },
                            modifier = Modifier
                                .weight(1.5f),
                            enabled = actualPage != pageSize - 1
                    ) {
                        Icon(
                                Icons.Rounded.KeyboardArrowRight,
                                contentDescription = ""
                        )
                    }

                    IconButton(
                            onClick = { actualPage = pageSize - 1},
                            modifier = Modifier
                                .weight(1.5f),
                            enabled = actualPage != pageSize - 1
                    ) {
                        Icon(
                                Icons.Rounded.SkipNext,
                                contentDescription = ""
                        )
                    }

                }
            }
    )

}

@Composable
fun TopAppBar() {
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
                    onClick = { } ,
                    modifier = Modifier
                        .weight(0.15f)
            ) {
                Icon(
                        Icons.Rounded.Menu ,
                        contentDescription = "Menu" ,
                        modifier = Modifier
                            .size(24.dp)
                )
            }

            Text(
                    "Countries list" ,
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
                        Icons.Rounded.Search ,
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

@Composable
private fun BodyList(
    innerPadding: PaddingValues ,
    countryModel: CountryViewModel ,
    countriesState: CountryState ,
    navController: NavController,
    actualPage: Int
) {
    LazyColumn(contentPadding = innerPadding) {

        countryModel.getAll()

        when (countriesState) {

            is CountryState.Loading -> item { Text("Loading") }

            is CountryState.Success.SuccessCountries -> {

                val countries = countriesState.countries

                val printedCountries = countries.subList(
                        actualPage * 9,
                        minOf(
                                (actualPage + 1) * 9,
                                countries.size
                        )
                )

                items(printedCountries) { country ->

                    var isExpanded by remember { mutableStateOf(false) }

                    Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                        vertical = 8.dp ,
                                        horizontal = 8.dp
                                )
                    ) {

                        Row(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .fillMaxWidth()
                        ) {

                            Image(
                                    painter = rememberAsyncImagePainter(model = country.flags[1]) ,
                                    contentDescription = "Image from internet" ,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(
                                                vertical = 16.dp,
                                                horizontal = 8.dp
                                        )
                                        .clip(CircleShape)
                                        .aspectRatio(1f)
                                        .weight(0.2f),
                                    contentScale = ContentScale.Crop
                            )

                            Column(
                                    modifier = Modifier
                                        .weight(0.7f) ,
                                    verticalArrangement = Arrangement.Center ,
                            ) {

                                Text(country.name.common)

                                Row {

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
                                                .weight(0.45f)
                                    )

                                    Text(
                                            text = "-" ,
                                            style = MaterialTheme.typography.bodyMedium ,
                                            modifier = Modifier
                                                .weight(0.1f)
                                    )


                                    Text(
                                            text = "${country.cca2}-${country.cca3}" ,
                                            style = MaterialTheme.typography.bodyMedium ,
                                            modifier = Modifier
                                                .weight(0.45f)
                                    )
                                }

                            }

                            IconButton(
                                    onClick = { isExpanded = !isExpanded } ,
                                    modifier = Modifier
                                        .weight(0.1f)
                            ) {
                                if (isExpanded) {
                                    Icon(
                                            Icons.Rounded.KeyboardArrowUp ,
                                            contentDescription = ""
                                    )
                                } else {
                                    Icon(
                                            Icons.Rounded.KeyboardArrowDown ,
                                            contentDescription = ""
                                    )
                                }
                            }
                        }

                        if (isExpanded) {
                            ExpandablePart(country , navController)
                        }

                        Divider(color = Color.LightGray)

                    }

                }
            }

            is CountryState.Error -> item { Text(countriesState.message) }

            else -> item { Text("No country available") }

        }


    }
}

@Composable
private fun ExpandablePart(
    country: CountriesData ,
    navController: NavController
) {
    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
    ) {

        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                            vertical = 4.dp
                    )
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
                    .padding(
                            vertical = 4.dp
                    )
        ) {
            Text(
                    text = "Official name :" ,
                    modifier = Modifier
                        .weight(0.5f)
            )

            Text(
                    text = country.name.official ,
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
                    .padding(
                            vertical = 4.dp
                    )
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
                    .padding(
                            vertical = 4.dp
                    )
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
                    .padding(
                            vertical = 4.dp
                    )
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
                    .border(
                            width = 1.dp ,
                            brush = Brush.linearGradient(listOf(Color.Black , Color.Black)) ,
                            shape = RoundedCornerShape(
                                    topStart = 4.dp ,
                                    topEnd = 4.dp ,
                                    bottomStart = 4.dp ,
                                    bottomEnd = 4.dp
                            )
                    )
                    .clickable {
                        navController.navigate("${Screen.CountryDetails.route}/${country.name.common}")
                    }
        ) {
            Text(
                    text = "See more" ,
                    style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.W500
                    ) ,
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(8.dp) ,
            )

            Icon(
                    Icons.Rounded.ArrowRightAlt ,
                    contentDescription = "" ,
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(8.dp)
            )
        }

    }
}