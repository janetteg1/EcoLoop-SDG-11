package com.example.a211390_ganthaithie_nelson_lab1.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a211390_ganthaithie_nelson_lab1.data.CarpoolListing
import com.example.a211390_ganthaithie_nelson_lab1.data.MarketItemListing
import com.example.a211390_ganthaithie_nelson_lab1.viewmodel.AppViewModel
import com.example.a211390_ganthaithie_nelson_lab1.viewmodel.CarpoolViewModel
import com.example.a211390_ganthaithie_nelson_lab1.viewmodel.MarketplaceViewModel
import com.example.a211390_ganthaithie_nelson_lab1.viewmodel.HomeAqiViewModel
import com.example.a211390_ganthaithie_nelson_lab1.ui.CarpoolScreenUiState
import com.example.a211390_ganthaithie_nelson_lab1.ui.MarketplaceScreenUiState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.LinearProgressIndicator
import com.example.a211390_ganthaithie_nelson_lab1.R
import kotlinx.coroutines.delay
import androidx.compose.material3.Surface
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.AlertDialog
import kotlinx.coroutines.launch
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@Composable
fun CoverScreen(onNavigateToHome: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
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

@Composable
fun BottomTabBar(
    selectedTab: Int,
    onHomeClick: () -> Unit = {},
    onCarpoolClick: () -> Unit,
    onMarketplaceClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = onHomeClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.DirectionsCar, contentDescription = null) },
            label = { Text("Carpool") },
            selected = selectedTab == 1,
            onClick = onCarpoolClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingBag, contentDescription = null) },
            label = { Text("Market") },
            selected = selectedTab == 2,
            onClick = onMarketplaceClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = selectedTab == 3,
            onClick = onProfileClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    selectedTab: Int,
    onCarpoolClick: () -> Unit,
    onMarketplaceClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val appState by viewModel.appState
    var nameInputValue by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.Transparent,
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
            BottomTabBar(
                selectedTab = 0,
                onHomeClick = { },
                onCarpoolClick = onCarpoolClick,
                onMarketplaceClick = onMarketplaceClick,
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ecoloop_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.3f),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // AQI banner at top
                val aqiVm: HomeAqiViewModel = viewModel()
                val aqiState = aqiVm.state.collectAsState()
                when (val s = aqiState.value) {
                    is com.example.a211390_ganthaithie_nelson_lab1.viewmodel.AqiUiState.Loading -> {
                        // small placeholder
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("🌿  Air quality · Bangi/UKM")
                                Text("Loading...", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    is com.example.a211390_ganthaithie_nelson_lab1.viewmodel.AqiUiState.Success -> {
                        val summary = s.summary
                        val statusTint = when (summary.aqiValue) {
                            in 0..40 -> MaterialTheme.colorScheme.tertiaryContainer
                            in 41..60 -> MaterialTheme.colorScheme.secondaryContainer
                            in 61..80 -> MaterialTheme.colorScheme.primaryContainer
                            in 81..100 -> MaterialTheme.colorScheme.errorContainer
                            else -> MaterialTheme.colorScheme.error
                        }
                        val bg = statusTint
                            .copy(alpha = 0.45f)
                            .compositeOver(MaterialTheme.colorScheme.surfaceContainerHigh)

                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = bg,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Column(modifier = Modifier
                                .padding(12.dp)) {
                                Text("🌿  Air quality · Bangi/UKM", fontWeight = FontWeight.SemiBold)
                                Text("${summary.level} (AQI ${summary.aqiValue})")
                                LinearProgressIndicator(progress = (summary.aqiValue / 100f).coerceIn(0f,1f), modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp))
                                Spacer(modifier = Modifier.height(6.dp))
                                val co2msg = when (summary.level) {
                                    "Good" -> "Great day to carpool — low pollution!"
                                    "Moderate" -> "Carpooling today saves ~2.1 kg CO₂"
                                    "Poor", "Very poor", "Extremely poor" -> "High pollution today — every carpool counts!"
                                    else -> "Consider reducing emissions today"
                                }
                                Text(co2msg, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    is com.example.a211390_ganthaithie_nelson_lab1.viewmodel.AqiUiState.Error -> {
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("🌿  Air quality · Bangi/UKM")
                                Text("Error: ${s.message}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                if (appState.currentUser.name.isEmpty()) {
                    Text(
                        text = "Welcome to EcoLoop",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = nameInputValue,
                        onValueChange = { nameInputValue = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your name") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.updateUserName(nameInputValue) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Get Started")
                    }
                } else {
                    Text(
                        text = "Welcome, ${appState.currentUser.name}!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onCarpoolClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Icon(Icons.Default.DirectionsCar, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Find a Ride")
                    }
                    Button(
                        onClick = onMarketplaceClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Icon(Icons.Default.ShoppingBag, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Browse Market")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarpoolScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onPostRide: () -> Unit,
    onSelectRide: (Int) -> Unit,
    onHomeClick: () -> Unit = {},
    onCarpoolClick: () -> Unit,
    onMarketplaceClick: () -> Unit,
    onProfileClick: () -> Unit,
    navController: NavController? = null
) {
    val carpoolViewModel: CarpoolViewModel = viewModel()
    val uiState by carpoolViewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var deleteConfirmId by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val filteredRides = when (uiState) {
        is CarpoolScreenUiState.Success -> {
            val rides = (uiState as CarpoolScreenUiState.Success).rides
            if (searchQuery.isBlank()) rides else rides.filter {
                it.pickupLocation.contains(searchQuery, ignoreCase = true) ||
                it.dropOffLocation.contains(searchQuery, ignoreCase = true) ||
                it.driverName.contains(searchQuery, ignoreCase = true)
            }
        }
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Available Rides") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onPostRide) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            BottomTabBar(
                selectedTab = 1,
                onHomeClick = onHomeClick,
                onCarpoolClick = onCarpoolClick,
                onMarketplaceClick = onMarketplaceClick,
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ecoloop_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.2f),
                contentScale = ContentScale.FillBounds
            )

            Column(modifier = Modifier.padding(padding)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search rides...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                when (uiState) {
                    is CarpoolScreenUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is CarpoolScreenUiState.Success -> {
                        if (filteredRides.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No rides available", style = MaterialTheme.typography.bodyLarge)
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp)
                            ) {
                                itemsIndexed(filteredRides) { index, ride ->
                                    RideCard(
                                        ride = ride,
                                        onClick = { onSelectRide(index) },
                                        onDelete = { deleteConfirmId = ride.id }
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                        }
                    }
                    is CarpoolScreenUiState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                (uiState as CarpoolScreenUiState.Error).message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }

        // Delete confirmation dialog
        if (deleteConfirmId != null) {
            AlertDialog(
                onDismissRequest = { deleteConfirmId = null },
                title = { Text("Delete Ride") },
                text = { Text("Are you sure you want to delete this ride?") },
                confirmButton = {
                    Button(
                        onClick = {
                            deleteConfirmId?.let { carpoolViewModel.deleteRide(it) }
                            deleteConfirmId = null
                            errorMessage = "Ride deleted"
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { deleteConfirmId = null }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Error snackbar
        if (errorMessage != null) {
            Snackbar(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(errorMessage ?: "")
            }
            LaunchedEffect(Unit) {
                delay(3000)
                errorMessage = null
            }
        }
    }
}

@Composable
fun RideCard(
    ride: CarpoolListing,
    onClick: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            val context = LocalContext.current
            val bmp = remember(ride.imageUrl) { loadBitmapFromString(context, ride.imageUrl) }
            bmp?.let { bitmap: Bitmap -> Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxWidth().height(140.dp), contentScale = ContentScale.Crop) }
            
            // Header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("${ride.pickupLocation} → ${ride.dropOffLocation}", fontWeight = FontWeight.Bold)
                    Text("${ride.seatsAvailable} seats • ${ride.price}", style = MaterialTheme.typography.bodySmall)
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ArrowBack else Icons.Default.Add,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Text(
                "Expired in ${ride.expiresInMinutes} min",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            // Expanded details
            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                androidx.compose.material3.Divider()
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow("Driver", ride.driverName)
                DetailRow("Vehicle", ride.vehicle)
                DetailRow("Plate", ride.plateNumber)
                DetailRow("Contact", ride.contactNow)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Text("Contact Driver")
                    }
                    if (onDelete != null) {
                        Button(
                            onClick = onDelete,
                            modifier = Modifier
                                .height(40.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Delete")
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Show more", fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onPostItem: () -> Unit,
    onSelectItem: (Int) -> Unit,
    onHomeClick: () -> Unit = {},
    onCarpoolClick: () -> Unit,
    onMarketplaceClick: () -> Unit,
    onProfileClick: () -> Unit,
    navController: NavController? = null
) {
    val marketplaceViewModel: MarketplaceViewModel = viewModel()
    val uiState by marketplaceViewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var deleteConfirmId by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val filteredItems = when (uiState) {
        is MarketplaceScreenUiState.Success -> {
            val items = (uiState as MarketplaceScreenUiState.Success).listings
            if (searchQuery.isBlank()) items else items.filter {
                it.itemName.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
            }
        }
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus Marketplace") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onPostItem) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            BottomTabBar(
                selectedTab = 2,
                onHomeClick = onHomeClick,
                onCarpoolClick = onCarpoolClick,
                onMarketplaceClick = onMarketplaceClick,
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ecoloop_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.2f),
                contentScale = ContentScale.FillBounds
            )

            Column(modifier = Modifier.padding(padding)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search items...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                when (uiState) {
                    is MarketplaceScreenUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is MarketplaceScreenUiState.Success -> {
                        if (filteredItems.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No items for sale", style = MaterialTheme.typography.bodyLarge)
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp)
                            ) {
                                itemsIndexed(filteredItems) { index, item ->
                                    ItemCard(
                                        item = item,
                                        onClick = { onSelectItem(index) },
                                        onDelete = { deleteConfirmId = item.id }
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                        }
                    }
                    is MarketplaceScreenUiState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                (uiState as MarketplaceScreenUiState.Error).message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }

        // Delete confirmation dialog
        if (deleteConfirmId != null) {
            AlertDialog(
                onDismissRequest = { deleteConfirmId = null },
                title = { Text("Delete Listing") },
                text = { Text("Are you sure you want to delete this listing?") },
                confirmButton = {
                    Button(
                        onClick = {
                            deleteConfirmId?.let { marketplaceViewModel.deleteListing(it) }
                            deleteConfirmId = null
                            errorMessage = "Listing deleted"
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { deleteConfirmId = null }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Error snackbar
        if (errorMessage != null) {
            Snackbar(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(errorMessage ?: "")
            }
            LaunchedEffect(Unit) {
                delay(3000)
                errorMessage = null
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCarpoolListingScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    val carpoolViewModel: CarpoolViewModel = viewModel()
    val appState by viewModel.appState
    val locationOptions = listOf("KPZ", "KIY", "KBH", "KUO", "PUSANIKA", "PSTL", "FTSM", "FST", "MRT KAJANG", "KTM UKM")

    var pickupLocation by remember { mutableStateOf(locationOptions.first()) }
    var dropOffLocation by remember { mutableStateOf(locationOptions.first()) }
    var driverName by remember { mutableStateOf(appState.currentUser.name) }
    var vehicle by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var seatsAvailable by remember { mutableStateOf("") }
    var expiresInMinutes by remember { mutableStateOf("") }
    var contactNow by remember { mutableStateOf(appState.currentUser.phone) }
    var imageUrl by remember { mutableStateOf("") }
    val context = LocalContext.current
    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var pickupExpanded by remember { mutableStateOf(false) }
    var dropOffExpanded by remember { mutableStateOf(false) }

    val pickLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            // load preview
            try {
                context.contentResolver.openInputStream(it)?.use { stream ->
                    previewBitmap = BitmapFactory.decodeStream(stream)
                    previewBitmap?.let { bmp ->
                        imageUrl = bitmapToDataUri(bmp)
                    }
                }
            } catch (_: Exception) { }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bmp: Bitmap? ->
        bmp?.let {
            // convert to base64 data uri
            imageUrl = bitmapToDataUri(it)
            previewBitmap = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Carpool") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ecoloop_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.2f),
                contentScale = ContentScale.FillBounds
            )

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    DropdownField(
                        label = "Pickup Location",
                        value = pickupLocation,
                        options = locationOptions,
                        expanded = pickupExpanded,
                        onExpandedChange = { pickupExpanded = it },
                        onValueSelected = {
                            pickupLocation = it
                            pickupExpanded = false
                        }
                    )
                    DropdownField(
                        label = "Drop Off Location",
                        value = dropOffLocation,
                        options = locationOptions,
                        expanded = dropOffExpanded,
                        onExpandedChange = { dropOffExpanded = it },
                        onValueSelected = {
                            dropOffLocation = it
                            dropOffExpanded = false
                        }
                    )
                    OutlinedTextField(
                        value = driverName,
                        onValueChange = { driverName = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Driver Name") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = vehicle,
                        onValueChange = { vehicle = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Vehicle") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = plateNumber,
                        onValueChange = { plateNumber = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Plate Number") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Price") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = seatsAvailable,
                        onValueChange = { seatsAvailable = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Seats Available") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = expiresInMinutes,
                        onValueChange = { expiresInMinutes = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Expired In (minutes)") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = contactNow,
                        onValueChange = { contactNow = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Contact Now") },
                        singleLine = true
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { pickLauncher.launch("image/*") }) { Text("Pick Image") }
                        Button(onClick = { cameraLauncher.launch(null) }) { Text("Take Photo") }
                    }
                    previewBitmap?.let { bmp ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(bitmap = bmp.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxWidth().height(180.dp), contentScale = ContentScale.Crop)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val postedAtNow = currentTimeString()
                            val isValid = pickupLocation.isNotBlank() && dropOffLocation.isNotBlank() &&
                                driverName.isNotBlank() && vehicle.isNotBlank() &&
                                plateNumber.isNotBlank() && price.isNotBlank() &&
                                seatsAvailable.isNotBlank() && expiresInMinutes.isNotBlank() &&
                                contactNow.isNotBlank()

                            if (isValid) {
                                carpoolViewModel.postRide(
                                    CarpoolListing(
                                        id = "",
                                        pickupLocation = pickupLocation,
                                        dropOffLocation = dropOffLocation,
                                        driverName = driverName,
                                        vehicle = vehicle,
                                        plateNumber = plateNumber,
                                        price = price,
                                        seatsAvailable = seatsAvailable,
                                        postedAt = postedAtNow,
                                        expiresInMinutes = expiresInMinutes,
                                        contactNow = contactNow,
                                        imageUrl = imageUrl
                                    )
                                )
                                onSubmit()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Post Carpool")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostMarketListingScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    val marketplaceViewModel: MarketplaceViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val conditionOptions = listOf("Used/Half New", "Brand New")
    val categoryOptions = listOf("household", "utensils", "cooking", "others")
    val locationOptions = listOf("KPZ", "KKM", "KUO", "KIY")

    var itemName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf(conditionOptions.first()) }
    var category by remember { mutableStateOf(categoryOptions.first()) }
    var location by remember { mutableStateOf(locationOptions.first()) }
    var contactNow by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    val context = LocalContext.current
    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var formError by remember { mutableStateOf<String?>(null) }

    val pickLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.openInputStream(it)?.use { stream ->
                    previewBitmap = BitmapFactory.decodeStream(stream)
                }
            } catch (_: Exception) { }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bmp: Bitmap? ->
        bmp?.let {
            imageUrl = bitmapToDataUri(it)
            previewBitmap = it
        }
    }

    var conditionExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var locationExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Market Item") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ecoloop_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.2f),
                contentScale = ContentScale.FillBounds
            )

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Item Name") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Price") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        label = { Text("Description") },
                        maxLines = 4
                    )
                    DropdownField(
                        label = "Condition",
                        value = condition,
                        options = conditionOptions,
                        expanded = conditionExpanded,
                        onExpandedChange = { conditionExpanded = it },
                        onValueSelected = {
                            condition = it
                            conditionExpanded = false
                        }
                    )
                    DropdownField(
                        label = "Category",
                        value = category,
                        options = categoryOptions,
                        expanded = categoryExpanded,
                        onExpandedChange = { categoryExpanded = it },
                        onValueSelected = {
                            category = it
                            categoryExpanded = false
                        }
                    )
                    DropdownField(
                        label = "Location",
                        value = location,
                        options = locationOptions,
                        expanded = locationExpanded,
                        onExpandedChange = { locationExpanded = it },
                        onValueSelected = {
                            location = it
                            locationExpanded = false
                        }
                    )
                    OutlinedTextField(
                        value = contactNow,
                        onValueChange = { contactNow = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Contact Now") },
                        singleLine = true
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { pickLauncher.launch("image/*") }) { Text("Pick Image") }
                        Button(onClick = { cameraLauncher.launch(null) }) { Text("Take Photo") }
                    }
                    previewBitmap?.let { bmp ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(bitmap = bmp.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxWidth().height(180.dp), contentScale = ContentScale.Crop)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val isValid = itemName.isNotBlank() && price.isNotBlank() &&
                                description.isNotBlank() && contactNow.isNotBlank()

                            if (!isValid) {
                                formError = "Please fill in Item Name, Price, Description, and Contact Now."
                                return@Button
                            }

                            formError = null

                            scope.launch {
                                val uploadedImageUrl = try {
                                    val bmp = previewBitmap
                                    if (bmp != null) {
                                        val output = ByteArrayOutputStream()
                                        bmp.compress(Bitmap.CompressFormat.JPEG, 75, output)
                                        val bytes = output.toByteArray()
                                        val ref = FirebaseStorage.getInstance()
                                            .reference
                                            .child("marketplace_images/${System.currentTimeMillis()}.jpg")
                                        ref.putBytes(bytes).await()
                                        ref.downloadUrl.await().toString()
                                    } else {
                                        ""
                                    }
                                } catch (_: Exception) {
                                    previewBitmap?.let { bitmapToDataUri(it) } ?: ""
                                }

                                val result = marketplaceViewModel.postListingAndAwait(
                                    MarketItemListing(
                                        id = "",
                                        itemName = itemName,
                                        price = price,
                                        description = description,
                                        condition = condition,
                                        category = category,
                                        location = location,
                                        contactNow = contactNow,
                                        imageUrl = uploadedImageUrl
                                    )
                                )
                                result.fold(
                                    onSuccess = { onSubmit() },
                                    onFailure = {
                                        formError = "Failed to post item. Please try again."
                                    }
                                )
                            }
                        },
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Post Item")
                    }

                    formError?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideDetailScreen(
    viewModel: AppViewModel,
    rideIndex: Int,
    onBack: () -> Unit,
    onContactClick: (String) -> Unit
) {
    val ride = viewModel.getRideByIndex(rideIndex)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ride Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        ride?.let {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ecoloop_background),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.2f),
                    contentScale = ContentScale.FillBounds
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    item {
                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val context = LocalContext.current
                                val bmp = remember(it.imageUrl) { loadBitmapFromString(context, it.imageUrl) }
                                bmp?.let { imageBitmap ->
                                    Image(
                                        bitmap = imageBitmap.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                                Text(
                                    text = "${it.pickupLocation} → ${it.dropOffLocation}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                DetailRow("Driver Name", it.driverName)
                                DetailRow("Vehicle", it.vehicle)
                                DetailRow("Plate Number", it.plateNumber)
                                DetailRow("Price", it.price)
                                DetailRow("Seats Available", it.seatsAvailable)
                                DetailRow("Posted At", it.postedAt)
                                DetailRow("Expired In (minutes)", it.expiresInMinutes)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = { onContactClick(it.id) }, modifier = Modifier.fillMaxWidth()) {
                                    Text("Contact Now: ${it.contactNow}")
                                }
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
fun ItemDetailScreen(
    viewModel: AppViewModel,
    itemIndex: Int,
    onBack: () -> Unit,
    onContactClick: (String) -> Unit
) {
    val item = viewModel.getItemByIndex(itemIndex)
    val appState by viewModel.appState
    val isFavorite = appState.favoriteItems.contains(item?.id ?: "")
    var expandedImage by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Item Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        item?.let {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ecoloop_background),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.2f),
                    contentScale = ContentScale.FillBounds
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    item {
                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val context = LocalContext.current
                                val bmp = remember(it.imageUrl) { loadBitmapFromString(context, it.imageUrl) }
                                bmp?.let { imageBitmap ->
                                    Image(
                                        bitmap = imageBitmap.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = it.itemName,
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    IconButton(onClick = { viewModel.toggleItemFavorite(it.id) }) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = if (isFavorite) "Remove favorite" else "Add favorite",
                                            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                DetailRow("Price", it.price)
                                DetailRow("Description", it.description)
                                DetailRow("Condition", it.condition)
                                DetailRow("Category", it.category)
                                DetailRow("Location", it.location)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = { onContactClick(it.id) }, modifier = Modifier.fillMaxWidth()) {
                                    Text("Contact Now: ${it.contactNow}")
                                }
                            }
                        }
                        if (expandedImage) {
                            Dialog(onDismissRequest = { expandedImage = false }) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black), contentAlignment = Alignment.Center) {
                                    val dlgContext = LocalContext.current
                                    val dlgBmp = remember(it.imageUrl) { loadBitmapFromString(dlgContext, it.imageUrl) }
                                    dlgBmp?.let { db ->
                                        Image(
                                            bitmap = db.asImageBitmap(),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                                .clickable { expandedImage = false },
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
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
fun ChatScreen(
    viewModel: AppViewModel,
    threadId: String,
    onBack: () -> Unit
) {
    val chatsMap by viewModel.chats
    val messages = chatsMap[threadId] ?: emptyList()
    var input by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(messages) { _, msg ->
                    val isMe = msg.sender == (viewModel.appState.value.currentUser.name.ifEmpty { "Anonymous" })
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                    ) {
                        Column(
                            modifier = Modifier
                                .background(if (isMe) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                                .widthIn(max = 260.dp)
                        ) {
                            Text(msg.sender, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(msg.text)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(formatTimestamp(msg.timestamp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.End)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Message...") }
                )
                Button(onClick = {
                    if (input.isNotBlank()) {
                        viewModel.postChatMessage(threadId, input)
                        input = ""
                    }
                }) {
                    Text("Send")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onHomeClick: () -> Unit = {},
    onCarpoolClick: () -> Unit,
    onMarketplaceClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val appState by viewModel.appState
    var email by remember { mutableStateOf(appState.currentUser.email) }
    var phone by remember { mutableStateOf(appState.currentUser.phone) }
    var location by remember { mutableStateOf(appState.currentUser.location) }

    LaunchedEffect(appState.currentUser) {
        email = appState.currentUser.email
        phone = appState.currentUser.phone
        location = appState.currentUser.location
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomTabBar(
                selectedTab = 3,
                onHomeClick = onHomeClick,
                onCarpoolClick = onCarpoolClick,
                onMarketplaceClick = onMarketplaceClick,
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ecoloop_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.2f),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = appState.currentUser.name.ifEmpty { "No Name Set" },
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Edit Contact Details",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email") },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Phone") },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Location") },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.updateUserContactDetails(email, phone, location) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Profile")
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Saved Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        DetailRow("Email", appState.currentUser.email.ifEmpty { "Not set" })
                        Divider()
                        DetailRow("Phone", appState.currentUser.phone.ifEmpty { "Not set" })
                        Divider()
                        DetailRow("Location", appState.currentUser.location.ifEmpty { "Not set" })
                        Divider()
                        DetailRow("Member Since", appState.currentUser.createdAt.ifEmpty { "Recently joined" })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownField(
    label: String,
    value: String,
    options: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueSelected: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { option ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(option) },
                    onClick = { onValueSelected(option) }
                )
            }
        }
    }
}

@Composable
private fun ItemCard(
    item: MarketItemListing,
    onClick: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            val context = LocalContext.current
            val bmp = remember(item.imageUrl) { loadBitmapFromString(context, item.imageUrl) }
                    when {
                        item.imageUrl.startsWith("http") -> {
                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        bmp != null -> {
                            Image(bitmap = bmp.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxWidth().height(140.dp), contentScale = ContentScale.Crop)
                        }
                    }
            
            // Header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(item.itemName, fontWeight = FontWeight.Bold, maxLines = 1, modifier = Modifier.fillMaxWidth())
                    Text("${item.price} • ${item.condition}", style = MaterialTheme.typography.bodySmall)
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ArrowBack else Icons.Default.Add,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Text(
                item.location,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            // Expanded details
            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                androidx.compose.material3.Divider()
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow("Description", item.description)
                DetailRow("Category", item.category)
                DetailRow("Condition", item.condition)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Text("View Details")
                    }
                    if (onDelete != null) {
                        Button(
                            onClick = onDelete,
                            modifier = Modifier
                                .height(40.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Delete")
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Show more", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun Divider() {
    androidx.compose.material3.Divider(modifier = Modifier.padding(vertical = 8.dp))
}

// Helpers for image handling
private fun bitmapToDataUri(bitmap: Bitmap): String {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
    val bytes = stream.toByteArray()
    val encoded = Base64.encodeToString(bytes, Base64.NO_WRAP)
    return "data:image/jpeg;base64,$encoded"
}

private fun dataUriToBitmap(dataUri: String): Bitmap? {
    return try {
        val base64 = dataUri.substringAfter("base64,")
        val bytes = Base64.decode(base64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    } catch (e: Exception) {
        null
    }
}

private fun loadBitmapFromString(context: android.content.Context, src: String): Bitmap? {
    return when {
        src.startsWith("content://") || src.startsWith("file://") -> {
            try {
                val uri = Uri.parse(src)
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    BitmapFactory.decodeStream(stream)
                }
            } catch (e: Exception) { null }
        }
        src.startsWith("data:image") -> dataUriToBitmap(src)
        src.startsWith("http") -> null // remote images not loaded here
        else -> null
    }
}

private fun formatTimestamp(millisString: String): String {
    return try {
        val ms = millisString.toLong()
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        sdf.format(Date(ms))
    } catch (e: Exception) { "" }
}

private fun currentTimeString(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date())
}
