# A211390 Gan Thai Thie_Nelson Project 2

An Android app built with Kotlin, Jetpack Compose, and Material 3 for the A211390 Gan Thai Thie_Nelson Lab1 project. The app includes campus-focused features such as home, carpool, marketplace, profile, posting flows, navigation, Firebase integration, and local data storage.

## Features

- Jetpack Compose UI with Material 3 theming
- Type-safe navigation with sealed routes
- Splash, Home, Carpool, Marketplace, Post, Detail, Profile, and Chat screens
- Firebase integration for analytics, Firestore, and Storage
- Room database for local persistence
- Retrofit networking support
- Coil image loading
- Kotlin coroutines and serialization
- FileProvider support for sharing stored media

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Room
- Firebase (Analytics, Firestore, Storage)
- Retrofit + Gson
- Coil 3
- Kotlin Serialization
- Coroutines

## Project Structure

- app/src/main/java/com/example/a211390_ganthaithie_nelson_lab1/ - app source code
- app/src/main/res/ - resources, themes, and drawables
- gradle/libs.versions.toml - version catalog
- app/google-services.json - Firebase configuration

## Requirements

- Android Studio
- JDK 11 or newer
- Android SDK with compileSdk 36
- A configured Firebase project if you want cloud features to work

## How to Run

1. Open the project in Android Studio.
2. Sync Gradle files.
3. Make sure app/google-services.json is present and valid.
4. Run the app on an emulator or physical device.

## Notes

- The app uses INTERNET permission for remote data access.
- The current app name is defined in app/src/main/res/values/strings.xml.
- The main entry point is app/src/main/java/com/example/a211390_ganthaithie_nelson_lab1/MainActivity.kt.

## Implementation Summary

For a more detailed breakdown of the screens, ViewModel layer, and theme setup, see IMPLEMENTATION_GUIDE.md.
