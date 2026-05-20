# EcoLoop Application - Navigation & Architecture Implementation

## Overview
This document summarizes the three main tasks completed for the EcoLoop Android application:
1. **Define Screens with Navigation** - Structured navigation flow
2. **ViewModel & Data Classes** - Centralized data management
3. **Material Design Integration** - Consistent Material 3 theming

---

## Task 1: Define Screens (More than 2 Screens)

### Screen Architecture
The app now implements **7 distinct screens** with type-safe navigation:

1. **Splash Screen** (`AppScreen.Splash`)
   - Initial loading screen with EcoLoop branding
   - 2-second display before navigation to Home
   - Shows app logo and loading indicator

2. **Home Screen** (`AppScreen.Home`)
   - Main hub with welcome message or user greeting
   - Navigation tabs for quick access
   - User onboarding with name input

3. **Carpool Screen** (`AppScreen.Carpool`)
   - Browse available rides
   - Search functionality for rides
   - Post new ride button
   - Detailed ride information display

4. **Marketplace Screen** (`AppScreen.Marketplace`)
   - Browse campus items for sale
   - Search and filter capabilities
   - Post new item button
   - Item categorization

5. **Post Listing Screen** (`AppScreen.PostListing`)
   - Create new ride or marketplace listing
   - Form inputs for item details
   - Form validation

6. **Ride Detail Screen** (`AppScreen.RideDetail`)
   - Detailed ride information
   - Driver details and contact
   - Favorite toggle
   - Contact functionality

7. **Item Detail Screen** (`AppScreen.ItemDetail`)
   - Detailed merchandise information
   - Seller profile and contact
   - Favorite toggle
   - Purchase inquiry

8. **Profile Screen** (`AppScreen.Profile`)
   - User profile information
   - Account details display
   - Profile settings

### Navigation Structure

**File**: `navigation/AppNavigation.kt`

```kotlin
sealed class AppScreen {
    @Serializable data object Splash : AppScreen()
    @Serializable data object Home : AppScreen()
    @Serializable data object Carpool : AppScreen()
    @Serializable data object Marketplace : AppScreen()
    @Serializable data object PostListing : AppScreen()
    @Serializable data class RideDetail(val rideIndex: Int) : AppScreen()
    @Serializable data class ItemDetail(val itemIndex: Int) : AppScreen()
    @Serializable data object Profile : AppScreen()
}
```

### Navigation Graph

**File**: `navigation/NavigationGraph.kt`

- Type-safe routing using Kotlin Serialization
- Automatic back stack management
- Deep link support ready
- Navigation transitions with proper state handling

### Screen Transitions

All screens handle navigation through `NavController`:
- Pop back stack for back navigation
- Deep link navigation for detail screens
- Proper save/restore of screen state

---

## Task 2: ViewModel & Data Classes

### Data Classes

**File**: `data/AppData.kt`

#### Comprehensive Data Models

```kotlin
@Serializable
data class RideListing(
    val id: String = "",
    val from: String = "",
    val to: String = "",
    val postedAt: String = "",
    val expiresAt: String = "",
    val seats: String = "",
    val price: String = "",
    val phone: String = "",
    val description: String = "",
    val driverName: String = "",
    val vehicleType: String = ""
)

@Serializable
data class MarketplaceItem(
    val id: String = "",
    val name: String = "",
    val price: String = "",
    val condition: String = "",
    val location: String = "",
    val description: String = "",
    val sellerName: String = "",
    val contact: String = "",
    val category: String = ""
)

@Serializable
data class UserProfile(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val avatar: String = "",
    val bio: String = "",
    val verificationStatus: Boolean = false,
    val createdAt: String = ""
)

data class AppState(
    val currentUser: UserProfile = UserProfile(),
    val allRides: List<RideListing> = emptyList(),
    val allItems: List<MarketplaceItem> = emptyList(),
    val favoriteRides: List<String> = emptyList(),
    val favoriteItems: List<String> = emptyList(),
    val rideSearchQuery: String = "",
    val itemSearchQuery: String = "",
    val filteredRides: List<RideListing> = emptyList(),
    val filteredItems: List<MarketplaceItem> = emptyList()
)
```

### ViewModel Implementation

**File**: `viewmodel/AppViewModel.kt`

The `AppViewModel` provides:

#### State Management
```kotlin
val appState: State<AppState> = _appState
```

#### Key Functions

1. **User Management**
   - `updateUserName(name: String)` - Update user profile

2. **Search Functionality**
   - `updateRideSearchQuery(query: String)` - Filter rides by location or driver
   - `updateItemSearchQuery(query: String)` - Filter items by name or category

3. **Favorites System**
   - `toggleRideFavorite(rideId: String)` - Add/remove ride from favorites
   - `toggleItemFavorite(itemId: String)` - Add/remove item from favorites

4. **Data Access**
   - `getRideByIndex(index: Int)` - Retrieve specific ride
   - `getItemByIndex(index: Int)` - Retrieve specific item

5. **Posting**
   - `postNewRide(ride: RideListing)` - Submit new ride listing
   - `postNewItem(item: MarketplaceItem)` - Submit new item listing

#### Benefits of ViewModel Architecture
✅ Single source of truth for all app data  
✅ Automatic state preservation across configuration changes  
✅ Lifecycle-aware data management  
✅ Reactive updates to UI  
✅ Easy testing with state isolation  

---

## Task 3: Material Design Integration

### Custom Material Theme

**File**: `material-theme/ui/theme/Theme.kt` & `Color.kt`

#### Color Scheme (Material 3)
- **Primary**: `#415F91` (Deep Blue)
- **Secondary**: `#565F71` (Slate)
- **Tertiary**: `#705575` (Purple)
- **Error**: `#BA1A1A` (Red)

#### Typography System
- **Display** fonts for headers
- **Title** fonts for section headers
- **Body** fonts for content
- **Label** fonts for ui elements

#### Theme Application

All composables use `MaterialTheme` tokens:

```kotlin
// Colors
MaterialTheme.colorScheme.primary
MaterialTheme.colorScheme.secondary
MaterialTheme.colorScheme.tertiary
MaterialTheme.colorScheme.error

// Typography
MaterialTheme.typography.displayMedium
MaterialTheme.typography.headlineSmall
MaterialTheme.typography.bodyMedium
MaterialTheme.typography.labelSmall
```

### Material Design Components

The app uses Material 3 components:
- ✅ `Scaffold` for layout structure
- ✅ `TopAppBar` for navigation headers
- ✅ `NavigationBar` for bottom navigation
- ✅ `FAB` (Floating Action Button) for primary actions
- ✅ `Cards` (`ElevatedCard`) for content grouping
- ✅ `Buttons` with proper styling
- ✅ `TextField` components with form states
- ✅ `Icon` components with proper theming

### Consistent Design Elements

- **Rounded Corners**: `RoundedCornerShape(12.dp)` or `RoundedCornerShape(16.dp)`
- **Spacing**: Consistent padding using `dp` units
- **Transparency**: Using Material color opacity for layering
- **Elevation**: Using Material elevation system
- **Animations**: Smooth content size transitions

---

## File Structure

```
app/src/main/java/com/example/a211390_ganthaithie_nelson_lab1/
├── MainActivity.kt                    # Entry point with Navigation setup
├── data/
│   └── AppData.kt                     # Data classes & AppState
├── viewmodel/
│   └── AppViewModel.kt                # State management & business logic
├── navigation/
│   ├── AppNavigation.kt               # Route definitions (sealed class)
│   └── NavigationGraph.kt             # Navigation implementation
├── screens/
│   └── Screens.kt                     # All 8 screen composables
└── ui/theme/
    ├── Color.kt                       # Material 3 color tokens
    ├── Theme.kt                       # Theme configuration
    └── Type.kt                        # Typography definitions
```

---

## Dependencies Added

**build.gradle.kts**
```gradle
// Navigation Compose - Type-safe navigation
implementation("androidx.navigation:navigation-compose:2.8.0")

// Lifecycle ViewModel Compose - ViewModel in Compose
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

// Kotlin Serialization - For @Serializable annotations
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

// Plugin for Kotlin Serialization
kotlin("plugin.serialization") version "1.9.20"
```

---

## Key Features Implemented

### 1. Navigation Benefits
- ✅ Type-safe navigation with compiled routing
- ✅ Automatic back stack management
- ✅ Deep link support built-in
- ✅ Configuration change handling
- ✅ Screen state preservation

### 2. ViewModel Benefits
- ✅ Centralized data management
- ✅ Lifecycle-aware
- ✅ Survives configuration changes
- ✅ Easy unit testing
- ✅ Single source of truth

### 3. Material Design Benefits
- ✅ Consistent visual language
- ✅ Professional appearance
- ✅ Accessibility compliance
- ✅ Modern UI patterns
- ✅ Brand consistency

---

## How to Build and Run

1. **Build the Project**
   ```bash
   ./gradlew build
   ```

2. **Run on Emulator/Device**
   ```bash
   ./gradlew installDebug
   ```

3. **Run Tests** (when configured)
   ```bash
   ./gradlew test
   ```

---

## Future Enhancements

- [ ] Remote API integration with ViewModel
- [ ] Room Database for local persistence
- [ ] User authentication flow
- [ ] Real-time messaging between users
- [ ] Image uploads for listings
- [ ] Payment integration
- [ ] Analytics tracking
- [ ] Push notifications

---

## Notes for Developers

1. **State Management**: Always use `viewModel.appState` to read data
2. **Navigation**: Use type-safe `navController.navigate(AppScreen.RouteName)`
3. **Theme**: Never hardcode colors; use `MaterialTheme.colorScheme.*`
4. **Data Serialization**: Add `@Serializable` annotation to new data classes
5. **Screen Creation**: Create new screens in `screens/Screens.kt` and add route in `navigation/NavigationGraph.kt`

---

## Summary

This implementation provides:
✅ **8+ Distinct Screens** with structured navigation flow  
✅ **Type-Safe Navigation** using Kotlin Sealed Classes  
✅ **Centralized Data Management** with AppViewModel  
✅ **Comprehensive Data Classes** for all app entities  
✅ **Material Design 3 Integration** with custom theme  
✅ **Professional Architecture** following Android best practices  

The app is now scalable, maintainable, and follows modern Android development patterns.
