@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.a211390_ganthaithie_nelson_lab1.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a211390_ganthaithie_nelson_lab1.data.CarpoolListing
import com.example.a211390_ganthaithie_nelson_lab1.data.MarketItemListing
import com.example.a211390_ganthaithie_nelson_lab1.viewmodel.CarpoolViewModel
import com.example.a211390_ganthaithie_nelson_lab1.viewmodel.MarketplaceViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListingScreen(
    navController: NavController,
    onBack: () -> Unit = { navController.popBackStack() }
) {
    val context = LocalContext.current
    val carpoolViewModel: CarpoolViewModel = viewModel()
    val marketplaceViewModel: MarketplaceViewModel = viewModel()
    
    var selectedTab by remember { mutableStateOf(0) }
    
    // Carpool form state
    var pickupLocation by remember { mutableStateOf("") }
    var dropOffLocation by remember { mutableStateOf("") }
    var driverName by remember { mutableStateOf("") }
    var vehicle by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var seatsAvailable by remember { mutableStateOf("") }
    var expiresInMinutes by remember { mutableStateOf("") }
    var contactNow by remember { mutableStateOf("") }
    var rideImageUri by remember { mutableStateOf<Uri?>(null) }
    var rideCameraUri by remember { mutableStateOf<Uri?>(null) }
    
    // Marketplace form state
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("New") }
    var category by remember { mutableStateOf("Electronics") }
    var location by remember { mutableStateOf("") }
    var itemContactNow by remember { mutableStateOf("") }
    var itemImageUri by remember { mutableStateOf<Uri?>(null) }
    var itemCameraUri by remember { mutableStateOf<Uri?>(null) }
    
    var isPosting by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (selectedTab == 0) {
            rideImageUri = uri
        } else {
            itemImageUri = uri
        }
    }

    val rideCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            rideCameraUri?.let { rideImageUri = it }
        }
    }

    val itemCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            itemCameraUri?.let { itemImageUri = it }
        }
    }
    
    val isCarpoolFormValid = pickupLocation.isNotBlank() && dropOffLocation.isNotBlank() &&
                            driverName.isNotBlank() && vehicle.isNotBlank() && 
                            plateNumber.isNotBlank() && price.isNotBlank() &&
                            seatsAvailable.isNotBlank() && contactNow.isNotBlank()
    
    val isMarketplaceFormValid = itemName.isNotBlank() && itemPrice.isNotBlank() &&
                                 description.isNotBlank() && location.isNotBlank() &&
                                 itemContactNow.isNotBlank()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post a Listing") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Tab Row
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Offer a Ride") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Sell an Item") }
                )
            }
            
            // Tab Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                when (selectedTab) {
                    0 -> {
                        // Carpool Form
                        CarpoolFormContent(
                            pickupLocation = pickupLocation,
                            onPickupLocationChange = { pickupLocation = it },
                            dropOffLocation = dropOffLocation,
                            onDropOffLocationChange = { dropOffLocation = it },
                            driverName = driverName,
                            onDriverNameChange = { driverName = it },
                            vehicle = vehicle,
                            onVehicleChange = { vehicle = it },
                            plateNumber = plateNumber,
                            onPlateNumberChange = { plateNumber = it },
                            price = price,
                            onPriceChange = { price = it },
                            seatsAvailable = seatsAvailable,
                            onSeatsAvailableChange = { seatsAvailable = it },
                            expiresInMinutes = expiresInMinutes,
                            onExpiresInMinutesChange = { expiresInMinutes = it },
                            contactNow = contactNow,
                            onContactNowChange = { contactNow = it },
                            onImagePick = { imagePickerLauncher.launch("image/*") },
                            onTakePhoto = {
                                val uri = createImageUri(context)
                                rideCameraUri = uri
                                rideCameraLauncher.launch(uri)
                            }
                        )
                    }
                    1 -> {
                        // Marketplace Form
                        MarketplaceFormContent(
                            itemName = itemName,
                            onItemNameChange = { itemName = it },
                            itemPrice = itemPrice,
                            onItemPriceChange = { itemPrice = it },
                            description = description,
                            onDescriptionChange = { description = it },
                            condition = condition,
                            onConditionChange = { condition = it },
                            category = category,
                            onCategoryChange = { category = it },
                            location = location,
                            onLocationChange = { location = it },
                            itemContactNow = itemContactNow,
                            onItemContactNowChange = { itemContactNow = it },
                            onImagePick = { imagePickerLauncher.launch("image/*") },
                            onTakePhoto = {
                                val uri = createImageUri(context)
                                itemCameraUri = uri
                                itemCameraLauncher.launch(uri)
                            }
                        )
                    }
                }
            }
            
            // Post Button
            Button(
                onClick = {
                    isPosting = true
                    when (selectedTab) {
                        0 -> {
                            val ride = CarpoolListing(
                                pickupLocation = pickupLocation,
                                dropOffLocation = dropOffLocation,
                                driverName = driverName,
                                vehicle = vehicle,
                                plateNumber = plateNumber,
                                price = price,
                                seatsAvailable = seatsAvailable,
                                expiresInMinutes = expiresInMinutes,
                                contactNow = contactNow,
                                imageUrl = rideImageUri?.toString() ?: "",
                                postedAt = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())
                            )
                            carpoolViewModel.postRide(ride)
                        }
                        1 -> {
                            val item = MarketItemListing(
                                itemName = itemName,
                                price = itemPrice,
                                description = description,
                                condition = condition,
                                category = category,
                                location = location,
                                contactNow = itemContactNow,
                                imageUrl = itemImageUri?.toString() ?: ""
                            )
                            marketplaceViewModel.postListing(item)
                        }
                    }
                    successMessage = "Listing posted successfully!"
                    isPosting = false
                    onBack()
                },
                enabled = (selectedTab == 0 && isCarpoolFormValid) || (selectedTab == 1 && isMarketplaceFormValid),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(16.dp)
            ) {
                if (isPosting) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Post")
                }
            }
        }
    }
}

@Composable
private fun CarpoolFormContent(
    pickupLocation: String,
    onPickupLocationChange: (String) -> Unit,
    dropOffLocation: String,
    onDropOffLocationChange: (String) -> Unit,
    driverName: String,
    onDriverNameChange: (String) -> Unit,
    vehicle: String,
    onVehicleChange: (String) -> Unit,
    plateNumber: String,
    onPlateNumberChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    seatsAvailable: String,
    onSeatsAvailableChange: (String) -> Unit,
    expiresInMinutes: String,
    onExpiresInMinutesChange: (String) -> Unit,
    contactNow: String,
    onContactNowChange: (String) -> Unit,
    onImagePick: () -> Unit,
    onTakePhoto: () -> Unit
) {
    androidx.compose.foundation.lazy.LazyColumn {
        items(1) {
            OutlinedTextField(
                value = pickupLocation,
                onValueChange = onPickupLocationChange,
                label = { Text("Pickup Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = dropOffLocation,
                onValueChange = onDropOffLocationChange,
                label = { Text("Drop-off Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = driverName,
                onValueChange = onDriverNameChange,
                label = { Text("Driver Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = vehicle,
                onValueChange = onVehicleChange,
                label = { Text("Vehicle") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = plateNumber,
                onValueChange = onPlateNumberChange,
                label = { Text("Plate Number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = price,
                onValueChange = onPriceChange,
                label = { Text("Price") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = seatsAvailable,
                onValueChange = onSeatsAvailableChange,
                label = { Text("Seats Available") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = expiresInMinutes,
                onValueChange = onExpiresInMinutesChange,
                label = { Text("Expires In (Minutes)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = contactNow,
                onValueChange = onContactNowChange,
                label = { Text("Contact Number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            Button(
                onClick = onImagePick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Pick Image")
            }

            Button(
                onClick = onTakePhoto,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("📷 Take Photo")
            }
        }
    }
}

@Composable
private fun MarketplaceFormContent(
    itemName: String,
    onItemNameChange: (String) -> Unit,
    itemPrice: String,
    onItemPriceChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    condition: String,
    onConditionChange: (String) -> Unit,
    category: String,
    onCategoryChange: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    itemContactNow: String,
    onItemContactNowChange: (String) -> Unit,
    onImagePick: () -> Unit,
    onTakePhoto: () -> Unit
) {
    androidx.compose.foundation.lazy.LazyColumn {
        items(1) {
            OutlinedTextField(
                value = itemName,
                onValueChange = onItemNameChange,
                label = { Text("Item Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = itemPrice,
                onValueChange = onItemPriceChange,
                label = { Text("Price") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                minLines = 3
            )
            
            // Condition Dropdown
            var expandedCondition by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedCondition,
                onExpandedChange = { expandedCondition = it }
            ) {
                OutlinedTextField(
                    value = condition,
                    onValueChange = { },
                    label = { Text("Condition") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCondition) }
                )
                ExposedDropdownMenu(
                    expanded = expandedCondition,
                    onDismissRequest = { expandedCondition = false }
                ) {
                    listOf("New", "Good", "Fair", "Poor").forEach { label ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                onConditionChange(label)
                                expandedCondition = false
                            }
                        )
                    }
                }
            }
            
            // Category Dropdown
            var expandedCategory by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedCategory,
                onExpandedChange = { expandedCategory = it }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { },
                    label = { Text("Category") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) }
                )
                ExposedDropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false }
                ) {
                    listOf("Books", "Electronics", "Clothing", "Other").forEach { label ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                onCategoryChange(label)
                                expandedCategory = false
                            }
                        )
                    }
                }
            }
            
            OutlinedTextField(
                value = location,
                onValueChange = onLocationChange,
                label = { Text("Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            OutlinedTextField(
                value = itemContactNow,
                onValueChange = onItemContactNowChange,
                label = { Text("Contact Number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            
            Button(
                onClick = onImagePick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Pick Image")
            }

            Button(
                onClick = onTakePhoto,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("📷 Take Photo")
            }
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val imageFile = File.createTempFile("listing_photo_", ".jpg", context.cacheDir)
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}
