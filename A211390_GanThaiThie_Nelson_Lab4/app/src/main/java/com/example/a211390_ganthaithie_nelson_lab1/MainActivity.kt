package com.example.a211390_ganthaithie_nelson_lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a211390_ganthaithie_nelson_lab1.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(dynamicColor = false) {
                ListingApp()
            }
        }
    }
}

data class RideData(
    val id: Int,
    val from: String,
    val to: String,
    val seats: Int,
    val price: String
)

data class ItemData(
    val id: Int,
    val title: String,
    val condition: String,
    val price: String,
    val location: String
)

class ListingViewModel : ViewModel() {
    private var nextRideId by mutableIntStateOf(4)
    private var nextItemId by mutableIntStateOf(4)

    val rides = mutableStateListOf(
        RideData(id = 1, from = "KPZ", to = "FTSM", seats = 3, price = "RM 4"),
        RideData(id = 2, from = "Pusanika", to = "MRT Kajang", seats = 2, price = "RM 8"),
        RideData(id = 3, from = "KIY", to = "Bangi Gateway", seats = 1, price = "RM 10")
    )

    val items = mutableStateListOf(
        ItemData(id = 1, title = "Study Lamp", condition = "Used", price = "RM 15", location = "KPZ"),
        ItemData(id = 2, title = "Electric Kettle", condition = "Like New", price = "RM 30", location = "KIY"),
        ItemData(id = 3, title = "Small Fan", condition = "Used", price = "RM 25", location = "KKM")
    )

    fun addRide(from: String, to: String, seats: Int, price: String) {
        rides.add(
            RideData(
                id = nextRideId++,
                from = from,
                to = to,
                seats = seats,
                price = price
            )
        )
    }

    fun addItem(title: String, condition: String, price: String, location: String) {
        items.add(
            ItemData(
                id = nextItemId++,
                title = title,
                condition = condition,
                price = price,
                location = location
            )
        )
    }

    fun getRideById(id: Int): RideData? = rides.firstOrNull { it.id == id }

    fun getItemById(id: Int): ItemData? = items.firstOrNull { it.id == id }
}

sealed class Screen(val route: String) {
    data object MyListings : Screen("my_listings")
    data object PostListing : Screen("post_listing/{type}") {
        fun createRoute(type: String): String = "post_listing/$type"
    }

    data object ListingDetail : Screen("listing_detail/{type}/{id}") {
        fun createRoute(type: String, id: Int): String = "listing_detail/$type/$id"
    }
}

@Composable
fun ListingApp(
    navController: NavHostController = rememberNavController(),
    viewModel: ListingViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = Screen.MyListings.route) {
        composable(Screen.MyListings.route) {
            MyListingsScreen(
                viewModel = viewModel,
                onPostRide = { navController.navigate(Screen.PostListing.createRoute("ride")) },
                onSellItem = { navController.navigate(Screen.PostListing.createRoute("item")) },
                onRideClick = { rideId ->
                    navController.navigate(Screen.ListingDetail.createRoute("ride", rideId))
                },
                onItemClick = { itemId ->
                    navController.navigate(Screen.ListingDetail.createRoute("item", itemId))
                }
            )
        }

        composable(
            route = Screen.PostListing.route,
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            val listingType = backStackEntry.arguments?.getString("type").orEmpty()
            PostListingScreen(
                listingType = listingType,
                viewModel = viewModel,
                onBack = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.ListingDetail.route,
            arguments = listOf(
                navArgument("type") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val listingType = backStackEntry.arguments?.getString("type").orEmpty()
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            ListingDetailScreen(
                listingType = listingType,
                id = id,
                viewModel = viewModel,
                onBack = { navController.navigateUp() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListingsScreen(
    viewModel: ListingViewModel,
    onPostRide: () -> Unit,
    onSellItem: () -> Unit,
    onRideClick: (Int) -> Unit,
    onItemClick: (Int) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("My Rides", "My Items")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Listings") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            if (selectedTab == 0) {
                Button(onClick = onPostRide, modifier = Modifier.fillMaxWidth()) {
                    Text("Post a Ride")
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(viewModel.rides) { ride ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onRideClick(ride.id) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "${ride.from} → ${ride.to}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text("Seats: ${ride.seats} | Price: ${ride.price}")
                            }
                        }
                    }
                }
            } else {
                Button(onClick = onSellItem, modifier = Modifier.fillMaxWidth()) {
                    Text("Sell an Item")
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(viewModel.items) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onItemClick(item.id) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text("${item.condition} | ${item.price} | ${item.location}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListingScreen(
    listingType: String,
    viewModel: ListingViewModel,
    onBack: () -> Unit
) {
    var fieldOne by remember { mutableStateOf("") }
    var fieldTwo by remember { mutableStateOf("") }
    var fieldThree by remember { mutableStateOf("") }
    var fieldFour by remember { mutableStateOf("") }

    val isRide = listingType == "ride"

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Post Listing") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (isRide) "Create a New Ride" else "Create a New Item",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            if (isRide) {
                OutlinedTextField(
                    value = fieldOne,
                    onValueChange = { fieldOne = it },
                    label = { Text("From") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = fieldTwo,
                    onValueChange = { fieldTwo = it },
                    label = { Text("To") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = fieldThree,
                    onValueChange = { fieldThree = it },
                    label = { Text("Seats") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = fieldFour,
                    onValueChange = { fieldFour = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        val seats = fieldThree.toIntOrNull() ?: 1
                        viewModel.addRide(fieldOne, fieldTwo, seats, fieldFour)
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = fieldOne.isNotBlank() && fieldTwo.isNotBlank() && fieldThree.isNotBlank() && fieldFour.isNotBlank()
                ) {
                    Text("Submit Ride")
                }
            } else {
                OutlinedTextField(
                    value = fieldOne,
                    onValueChange = { fieldOne = it },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = fieldTwo,
                    onValueChange = { fieldTwo = it },
                    label = { Text("Condition") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = fieldThree,
                    onValueChange = { fieldThree = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = fieldFour,
                    onValueChange = { fieldFour = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        viewModel.addItem(fieldOne, fieldTwo, fieldThree, fieldFour)
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = fieldOne.isNotBlank() && fieldTwo.isNotBlank() && fieldThree.isNotBlank() && fieldFour.isNotBlank()
                ) {
                    Text("Submit Item")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingDetailScreen(
    listingType: String,
    id: Int,
    viewModel: ListingViewModel,
    onBack: () -> Unit
) {
    val ride = if (listingType == "ride") viewModel.getRideById(id) else null
    val item = if (listingType == "item") viewModel.getItemById(id) else null

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Listing Detail") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (ride != null) {
                        Text("Type: Ride", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("From: ${ride.from}")
                        Text("To: ${ride.to}")
                        Text("Seats: ${ride.seats}")
                        Text("Price: ${ride.price}")
                    } else if (item != null) {
                        Text("Type: Item", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Name: ${item.title}")
                        Text("Condition: ${item.condition}")
                        Text("Price: ${item.price}")
                        Text("Location: ${item.location}")
                    } else {
                        Text("Listing not found")
                    }
                }
            }

            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Back")
            }
        }
    }
}
