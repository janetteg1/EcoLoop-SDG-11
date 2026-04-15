package com.example.a211390_ganthaithie_nelson_lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a211390_ganthaithie_nelson_lab3.R
import com.example.a211390_ganthaithie_nelson_lab1.ui.theme.AppTheme
import kotlinx.coroutines.delay

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

// Shared Mock Data
val allRides = listOf(
    RideListing("Kolej Ibu Zain", "FTSM", "10:00 AM", "30", "3", "RM 3", "0112233445"),
    RideListing("Pusanika", "FTSM", "1:30 PM", "10", "2", "RM 5", "0123456789"),
    RideListing("Pusanika", "MRT Kajang", "2:30 PM", "15", "1", "RM 10", "0119988776"),
    RideListing("KKM", "Bangi Gateway", "5:00 PM", "45", "2", "RM 8", "0193344556")
)

val allItems = listOf(
    MarketplaceItem("Table Lamp", "RM 15", "Used", "KPZ", "Perfect for late night studying."),
    MarketplaceItem("Electric Kettle", "RM 30", "Used", "KPZ", "Working perfectly, moving out soon."),
    MarketplaceItem("Study Lamp", "RM 15", "Like New", "KIY", "Very bright LED lamp."),
    MarketplaceItem("Small Fan", "RM 30", "Like New", "KIY", "Silent and powerful."),
    MarketplaceItem("Bedding Set", "RM 50", "New", "KKM", "Single size, 100% cotton.")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Root Setup: Wrapping content with the exported theme and disabling dynamicColor
            AppTheme(dynamicColor = false) {
                EcoLoopApp()
            }
        }
    }
}

@Composable
fun AppBackground() {
    Image(
        painter = painterResource(id = R.drawable.ecoloop_background),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.3f),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun EcoLoopApp() {
    var currentScreen by remember { mutableStateOf("splash") }
    var previousScreen by remember { mutableStateOf("home") }
    var selectedItem by remember { mutableStateOf<Any?>(null) }

    // State variables for Student Login
    var nameInput by remember { mutableStateOf("") }
    var displayedName by remember { mutableStateOf("") }

    // State variables for global search queries
    var rideSearchQuery by remember { mutableStateOf("") }
    var itemSearchQuery by remember { mutableStateOf("") }

    // Splash Screen Transition
    LaunchedEffect(currentScreen) {
        if (currentScreen == "splash") {
            delay(2000) // Display splash for 2 seconds
            currentScreen = "home"
        }
    }

    // Handle Back Navigation
    BackHandler(enabled = currentScreen != "home" && currentScreen != "splash") {
        if (currentScreen == "detail" || currentScreen == "post_listing") {
            currentScreen = previousScreen
        } else {
            currentScreen = "home"
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AppBackground()
        
        when (currentScreen) {
            "splash" -> CoverScreen()
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
                onLoginClick = { displayedName = nameInput },
                rideSearchValue = rideSearchQuery,
                onRideSearchChange = { rideSearchQuery = it },
                itemSearchValue = itemSearchQuery,
                onItemSearchChange = { itemSearchQuery = it }
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
                },
                searchQuery = rideSearchQuery
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
                },
                searchQuery = itemSearchQuery
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
}

@Composable
fun CoverScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ecoloop_logo),
                    contentDescription = "EcoLoop Logo",
                    modifier = Modifier.size(180.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "EcoLoop",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "UKM Campus Connect",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                letterSpacing = 2.sp
            )
            
            Spacer(modifier = Modifier.height(60.dp))
            androidx.compose.material3.CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                strokeWidth = 3.dp
            )
        }
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
    onLoginClick: () -> Unit,
    rideSearchValue: String,
    onRideSearchChange: (String) -> Unit,
    itemSearchValue: String,
    onItemSearchChange: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Color.Transparent, // Allow background to show through
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ecoloop_logo),
                            contentDescription = "EcoLoop Logo",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("EcoLoop", fontWeight = FontWeight.ExtraBold) 
                    }
                }
            )
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
        ) {
            // Aesthetic Header for the User Tab
            if (selectedTab == 2 && userName.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(188.dp)
                        .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceBright.copy(alpha = 0.26f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(52.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Welcome back,",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                when (selectedTab) {
                    0 -> {
                        // My Rides Tab
                        OutlinedTextField(
                            value = rideSearchValue,
                            onValueChange = onRideSearchChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search My Rides (Location or Price)...") },
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        val matchesRidesSearch = "Booked Rides".contains(rideSearchValue, ignoreCase = true) || 
                                               "No booked rides yet.".contains(rideSearchValue, ignoreCase = true) ||
                                               "BOOK A RIDE".contains(rideSearchValue, ignoreCase = true) ||
                                               "POST A RIDE".contains(rideSearchValue, ignoreCase = true)

                        if (matchesRidesSearch || rideSearchValue.isEmpty()) {
                            Text(
                                text = "Booked Rides",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No booked rides yet.", color = MaterialTheme.colorScheme.onSurfaceVariant)

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
                            text = "Recent Rides",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            val filteredRides = allRides.filter { it.from.contains(rideSearchValue, ignoreCase = true) || it.to.contains(rideSearchValue, ignoreCase = true) || it.price.contains(rideSearchValue, ignoreCase = true) }
                            items(filteredRides) { ride ->
                                ProductItemRow("Ride to ${ride.to}", ride.price, ride.from, onClick = {
                                    onSelectItem(ride)
                                })
                            }
                        }
                    }
                    1 -> {
                        // My Items Tab
                        OutlinedTextField(
                            value = itemSearchValue,
                            onValueChange = onItemSearchChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search My Items (Name or Location)...") },
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        val matchesItemsSearch = "Purchased Items".contains(itemSearchValue, ignoreCase = true) || 
                                               "No purchased items yet.".contains(itemSearchValue, ignoreCase = true) ||
                                               "PURCHASE AN ITEM".contains(itemSearchValue, ignoreCase = true) ||
                                               "SELL AN ITEM".contains(itemSearchValue, ignoreCase = true)

                        if (matchesItemsSearch || itemSearchValue.isEmpty()) {
                            Text(
                                text = "Purchased Items",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No purchased items yet.", color = MaterialTheme.colorScheme.onSurfaceVariant)

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
                            text = "Recent Items",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            val filteredItems = allItems.filter { it.name.contains(itemSearchValue, ignoreCase = true) || it.location.contains(itemSearchValue, ignoreCase = true) }
                            items(filteredItems) { item ->
                                ProductItemRow(item.name, item.price, item.location, onClick = {
                                    onSelectItem(item)
                                })
                            }
                        }
                    }
                    2 -> {
                        if (userName.isEmpty()) {
                            // User Tab - Student Login
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                ElevatedCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.elevatedCardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.92f),
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(120.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                Icons.Default.Person,
                                                contentDescription = null,
                                                modifier = Modifier.size(80.dp),
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(18.dp))
                                        Text(
                                            text = "Student Login",
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = MaterialTheme.colorScheme.primary,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "Join the EcoLoop community",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.height(20.dp))

                                        OutlinedTextField(
                                            value = nameInputValue,
                                            onValueChange = onNameInputChange,
                                            label = { Text("Name") },
                                            placeholder = { Text("Enter your name") },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            singleLine = true,
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.9f),
                                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.8f),
                                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                                cursorColor = MaterialTheme.colorScheme.primary
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(14.dp))

                                        Button(
                                            onClick = onLoginClick,
                                            modifier = Modifier.fillMaxWidth().height(56.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        } else {
                            // Profile details after login
                            Column(modifier = Modifier.fillMaxSize()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Account Details",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Manage your student profile",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(14.dp))

                                ElevatedCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.elevatedCardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.92f),
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        ProfileItem("Matric No", "A211390")
                                        ProfileItem("Email", "A211390@siswa.ukm.edu.my")
                                        ProfileItem("University", "UKM")
                                    }
                                }
                                
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = { onNameInputChange(""); onLoginClick() },
                                    modifier = Modifier.fillMaxWidth().height(56.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                    ),
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
fun EcoLoopItemCard(
    title: String,
    subtitle: String,
    tag: String,
    price: String,
    details: String,
    onClickContact: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded }
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = tag,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Price/Info:",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = price,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onClickContact,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Contact Now")
                }
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.85f),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MainActionCard(title: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ElevatedCard(
        modifier = modifier
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
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
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarpoolScreen(
    onBack: () -> Unit, 
    onPostRide: () -> Unit, 
    onSelectRide: (RideListing) -> Unit,
    searchQuery: String = ""
) {
    Scaffold(
        containerColor = Color.Transparent,
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
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Post Ride")
            }
        }
    ) { padding ->
        val filteredRides = if (searchQuery.isEmpty()) allRides else allRides.filter {
            it.from.contains(searchQuery, ignoreCase = true) || it.to.contains(searchQuery, ignoreCase = true)
        }

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredRides) { ride ->
                RideCard(ride, onClick = { onSelectRide(ride) })
            }
        }
    }
}

@Composable
fun RideCard(ride: RideListing, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) } // Interactive UX: click-to-expand state

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded } // Interactive UX: toggle expansion
            .animateContentSize(), // Interactive UX: smooth transition
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f), // Material 3 Standard: Surface token
            contentColor = MaterialTheme.colorScheme.onSurface // Material 3 Standard: OnSurface token
        )
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
                    color = MaterialTheme.colorScheme.primary, // Color Logic: Theme token
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Text(
                text = "To: ${ride.to}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Posted at: ${ride.postedAt}", 
                        style = MaterialTheme.typography.bodySmall, 
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                    style = MaterialTheme.typography.bodySmall, 
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("View Full Details")
                }
            } else {
                Text(
                    text = "Tap to see more info",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(
    onBack: () -> Unit, 
    onPostItem: () -> Unit, 
    onSelectItem: (MarketplaceItem) -> Unit,
    searchQuery: String = ""
) {
    Scaffold(
        containerColor = Color.Transparent,
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
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Post Item")
            }
        }
    ) { padding ->
        val filteredItems = if (searchQuery.isEmpty()) allItems else allItems.filter {
            it.name.contains(searchQuery, ignoreCase = true) || it.location.contains(searchQuery, ignoreCase = true)
        }

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredItems) { item ->
                ItemCard(item, onClick = { onSelectItem(item) })
            }
        }
    }
}

@Composable
fun ItemCard(item: MarketplaceItem, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.ShoppingBag, 
                        null, 
                        tint = MaterialTheme.colorScheme.onSurfaceVariant, 
                        modifier = Modifier.size(40.dp)
                    )
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
                    Text("Location: ${item.location}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            if (expanded) {
                Spacer(Modifier.height(12.dp))
                Text(item.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Details & Chat")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListingScreen(onBack: () -> Unit, onSubmit: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
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
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Add Image", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value = "", 
                onValueChange = {}, 
                label = { Text("From") }, 
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                )
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = "", 
                onValueChange = {}, 
                label = { Text("To") }, 
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                )
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = "", 
                onValueChange = {}, 
                label = { Text("Expired in (minutes)") }, 
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                )
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = "", 
                onValueChange = {}, 
                label = { Text("Price") }, 
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                )
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = "", 
                onValueChange = {}, 
                label = { Text("Seats Available") }, 
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                )
            )
            
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
        containerColor = Color.Transparent,
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
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.85f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (item is RideListing) Icons.Default.DirectionsCar else Icons.Default.ShoppingBag,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (name.contains("Ride") || name.contains("Carpool")) Icons.Default.DirectionsCar else Icons.Default.ShoppingBag,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text("$info \u2022 $location", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppTheme {
        EcoLoopApp()
    }
}
