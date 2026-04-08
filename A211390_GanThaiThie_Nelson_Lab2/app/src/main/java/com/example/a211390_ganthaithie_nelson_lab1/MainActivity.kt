package com.example.a211390_ganthaithie_nelson_lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a211390_ganthaithie_nelson_lab1.ui.theme.A211390_GanThaiThie_Nelson_Lab1Theme

// Data classes
data class RideListing(
    val from: String,
    val to: String,
    val postedAt: String,
    val expiresAt: String,
    val seats: String,
    val price: String,
    val phone: String,
    val description: String = "Campus carpool ride for UKM students."
)

data class MarketplaceItem(
    val name: String,
    val price: String,
    val condition: String,
    val location: String,
    val description: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A211390_GanThaiThie_Nelson_Lab1Theme {
                EcoLoopApp()
            }
        }
    }
}

@Composable
fun EcoLoopApp() {
    var currentScreen by remember { mutableStateOf("home") }
    var previousScreen by remember { mutableStateOf("home") }
    var selectedItem by remember { mutableStateOf<Any?>(null) }

    // State variables for Student Login
    var nameInput by remember { mutableStateOf("") }
    var displayedName by remember { mutableStateOf("") }

    when (currentScreen) {
        "home" -> HomeScreen(
            onNavigate = { 
                previousScreen = "home"
                currentScreen = it 
            },
            onSelectItem = { item ->
                selectedItem = item
                previousScreen = "home"
                currentScreen = "detail"
            },
            userName = displayedName,
            nameInputValue = nameInput,
            onNameInputChange = { nameInput = it },
            onLoginClick = { displayedName = nameInput }
        )
        "carpool" -> CarpoolScreen(
            onBack = { currentScreen = "home" },
            onPostRide = { 
                previousScreen = "carpool"
                currentScreen = "post_listing" 
            },
            onSelectRide = { ride ->
                selectedItem = ride
                previousScreen = "carpool"
                currentScreen = "detail"
            }
        )
        "marketplace" -> MarketplaceScreen(
            onBack = { currentScreen = "home" },
            onPostItem = { 
                previousScreen = "marketplace"
                currentScreen = "post_listing" 
            },
            onSelectItem = { item ->
                selectedItem = item
                previousScreen = "marketplace"
                currentScreen = "detail"
            }
        )
        "post_listing" -> PostListingScreen(
            onBack = { currentScreen = previousScreen },
            onSubmit = { currentScreen = previousScreen }
        )
        "detail" -> DetailScreen(
            item = selectedItem,
            onBack = { currentScreen = previousScreen }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit, 
    onSelectItem: (Any) -> Unit,
    userName: String,
    nameInputValue: String,
    onNameInputChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    // State for searching across the entire tab content
    var rideSearchQuery by remember { mutableStateOf("") }
    var itemSearchQuery by remember { mutableStateOf("") }

    val recentRides = listOf(
        RideListing("Pusanika", "FTSM", "1:30 PM", "10", "2", "RM 5", "0123456789")
    )
    
    val recentItems = listOf(
        MarketplaceItem("Electric Kettle", "RM 30", "Used", "KPZ", "Working perfectly, moving out soon."),
        MarketplaceItem("Study Lamp", "RM 15", "Like New", "KIY", "Very bright LED lamp.")
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("EcoLoop", fontWeight = FontWeight.ExtraBold) })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.DirectionsCar, contentDescription = null) },
                    label = { Text("My Rides") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingBag, contentDescription = null) },
                    label = { Text("My Items") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("User") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Aesthetic Header for the User Tab
            if (selectedTab == 2 && userName.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Welcome back,",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                when (selectedTab) {
                    0 -> {
                        // My Rides Tab
                        // Requirement: Search bar at the top of the tab
                        OutlinedTextField(
                            value = rideSearchQuery,
                            onValueChange = { rideSearchQuery = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search My Rides (Location or Price)...") },
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Requirement: Searching within the page content
                        val matchesRidesSearch = "Booked Rides".contains(rideSearchQuery, ignoreCase = true) || 
                                               "No booked rides yet.".contains(rideSearchQuery, ignoreCase = true) ||
                                               "BOOK A RIDE".contains(rideSearchQuery, ignoreCase = true) ||
                                               "POST A RIDE".contains(rideSearchQuery, ignoreCase = true)

                        if (matchesRidesSearch || rideSearchQuery.isEmpty()) {
                            Text(
                                text = "Booked Rides",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No booked rides yet.", color = Color.Gray)

                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                MainActionCard(
                                    title = "BOOK A RIDE",
                                    icon = Icons.Default.DirectionsCar,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onNavigate("carpool") }
                                )
                                MainActionCard(
                                    title = "POST A RIDE",
                                    icon = Icons.Default.Add,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onNavigate("carpool") }
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))
                        }
                        
                        Text(
                            text = "Recent Listings",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            val filteredRides = recentRides.filter { it.from.contains(rideSearchQuery, ignoreCase = true) || it.to.contains(rideSearchQuery, ignoreCase = true) || it.price.contains(rideSearchQuery, ignoreCase = true) }
                            items(filteredRides) { ride ->
                                ProductItemRow("Ride to ${ride.to}", ride.price, ride.from, onClick = {
                                    onSelectItem(ride)
                                })
                            }
                        }
                    }
                    1 -> {
                        // My Items Tab
                        // Requirement: Search bar at the top of the tab
                        OutlinedTextField(
                            value = itemSearchQuery,
                            onValueChange = { itemSearchQuery = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search My Items (Name or Location)...") },
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Requirement: Searching within the page content
                        val matchesItemsSearch = "Purchased Items".contains(itemSearchQuery, ignoreCase = true) || 
                                               "No purchased items yet.".contains(itemSearchQuery, ignoreCase = true) ||
                                               "PURCHASE AN ITEM".contains(itemSearchQuery, ignoreCase = true) ||
                                               "SELL AN ITEM".contains(itemSearchQuery, ignoreCase = true)

                        if (matchesItemsSearch || itemSearchQuery.isEmpty()) {
                            Text(
                                text = "Purchased Items",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No purchased items yet.", color = Color.Gray)

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                MainActionCard(
                                    title = "PURCHASE AN ITEM",
                                    icon = Icons.Default.ShoppingBag,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onNavigate("marketplace") }
                                )
                                MainActionCard(
                                    title = "SELL AN ITEM",
                                    icon = Icons.Default.Add,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onNavigate("marketplace") }
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))
                        }

                        Text(
                            text = "Recent Listings",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            val filteredItems = recentItems.filter { it.name.contains(itemSearchQuery, ignoreCase = true) || it.location.contains(itemSearchQuery, ignoreCase = true) }
                            items(filteredItems) { item ->
                                ProductItemRow(item.name, item.price, item.location, onClick = {
                                    onSelectItem(item)
                                })
                            }
                        }
                    }
                    2 -> {
                        if (userName.isEmpty()) {
                            // User Tab - Student Login (Centered)
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp),
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = "Student Login",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Join the EcoLoop community",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                
                                OutlinedTextField(
                                    value = nameInputValue,
                                    onValueChange = onNameInputChange,
                                    label = { Text("Name") },
                                    placeholder = { Text("Enter your name") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    singleLine = true
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Button(
                                    onClick = onLoginClick,
                                    modifier = Modifier.fillMaxWidth().height(56.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            // Profile details after login
                            Column(modifier = Modifier.fillMaxSize()) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Account Details",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                ProfileItem("Matric No", "A211390")
                                ProfileItem("Email", "A211390@siswa.ukm.edu.my")
                                ProfileItem("University", "UKM")
                                
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = { onNameInputChange(""); onLoginClick() },
                                    modifier = Modifier.fillMaxWidth().height(56.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.error),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Logout")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontWeight = FontWeight.Medium, color = Color.Gray)
            Text(text = value, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MainActionCard(title: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarpoolScreen(onBack: () -> Unit, onPostRide: () -> Unit, onSelectRide: (RideListing) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carpool Rides") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onPostRide,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Post Ride")
            }
        }
    ) { padding ->
        val rides = listOf(
            RideListing("Kolej Ibu Zain", "FTSM", "10:00 AM", "30", "3", "RM 3", "0112233445"),
            RideListing("Pusanika", "MRT Kajang", "2:30 PM", "15", "1", "RM 10", "0119988776"),
            RideListing("KKM", "Bangi Gateway", "5:00 PM", "45", "2", "RM 8", "0193344556")
        )
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(rides) { ride ->
                RideCard(ride, onClick = { onSelectRide(ride) })
            }
        }
    }
}

@Composable
fun RideCard(ride: RideListing, onClick: () -> Unit) {
    var message by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "From: ${ride.from}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = ride.price,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Text(
                text = "To: ${ride.to}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Posted at: ${ride.postedAt}", 
                    fontSize = 14.sp, 
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${ride.seats} seats left",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "Expired in: ${ride.expiresAt} minutes", 
                fontSize = 14.sp, 
                color = Color.Gray
            )
            
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("View Details")
            }

            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Your Message") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ask about the ride...") }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(onBack: () -> Unit, onPostItem: () -> Unit, onSelectItem: (MarketplaceItem) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Marketplace") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onPostItem,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Post Item")
            }
        }
    ) { padding ->
        val itemsList = listOf(
            MarketplaceItem("Table Lamp", "RM 15", "Used", "KPZ", "Perfect for late night studying."),
            MarketplaceItem("Small Fan", "RM 30", "Like New", "KIY", "Silent and powerful."),
            MarketplaceItem("Bedding Set", "RM 50", "New", "KKM", "Single size, 100% cotton.")
        )
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(itemsList) { item ->
                ItemCard(item, onClick = { onSelectItem(item) })
            }
        }
    }
}

@Composable
fun ItemCard(item: MarketplaceItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ShoppingBag, null, tint = Color.White, modifier = Modifier.size(40.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(item.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(
                    text = item.price,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )
                Text("Condition: ${item.condition}", style = MaterialTheme.typography.labelSmall)
                Text("Location: ${item.location}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { /* Detail */ onClick() },
                    modifier = Modifier.height(36.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                ) {
                    Text("Chat", fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListingScreen(onBack: () -> Unit, onSubmit: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post New Listing") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(40.dp), tint = Color.DarkGray)
                    Text("Add Image", color = Color.DarkGray)
                }
            }
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("From") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("To") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Expired in (minutes)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Price") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Seats Available") }, modifier = Modifier.fillMaxWidth())
            
            Spacer(Modifier.weight(1f))
            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Submit Listing", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(item: Any?, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listing Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.LightGray, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (item is RideListing) Icons.Default.DirectionsCar else Icons.Default.ShoppingBag,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color.White
                )
            }
            Spacer(Modifier.height(24.dp))
            
            when (item) {
                is MarketplaceItem -> {
                    Text(item.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(
                        item.price,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Location: ${item.location}", style = MaterialTheme.typography.bodyLarge)
                    Text("Condition: ${item.condition}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(16.dp))
                    Text(item.description, style = MaterialTheme.typography.bodyMedium)
                }
                is RideListing -> {
                    Text("From: ${item.from}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("To: ${item.to}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(
                        item.price,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Posted at: ${item.postedAt}", style = MaterialTheme.typography.titleMedium)
                    Text("Expired in: ${item.expiresAt} minutes", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(12.dp))
                    Text("Seats Available: ${item.seats}", style = MaterialTheme.typography.bodyLarge)
                    Text("Driver Phone: ${item.phone}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(16.dp))
                    Text(item.description, style = MaterialTheme.typography.bodyMedium)
                }
            }
            
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { /* Contact */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Contact Now", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ProductItemRow(name: String, info: String, location: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (info.contains("Seats") || info.startsWith("RM")) {
                         if (name.contains("Ride") || name.contains("Carpool")) Icons.Default.DirectionsCar else Icons.Default.ShoppingBag
                    } else Icons.Default.ShoppingBag,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text("$info \u2022 $location", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    A211390_GanThaiThie_Nelson_Lab1Theme {
        EcoLoopApp()
    }
}
