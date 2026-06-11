# EcoLoop — A211390_GanThaiThie_Nelson_Project

> Campus carpool and marketplace Android app built with Jetpack Compose.

## Overview
- Simple campus-focused app that lets users post/find carpool rides and list/browse marketplace items.
- Built with Kotlin and Jetpack Compose; contains screens, simple in-memory state, and image handling (local URIs & data-URI images).

## Key files
- App module: `app/`
- Main UI: `app/src/main/java/com/example/a211390_ganthaithie_nelson_lab1/screens/Screens.kt`

## Requirements
- Android Studio (Arctic Fox or later recommended)
- JDK 11 or newer

## Build & run
Open the project in Android Studio and run on an emulator or device, or use the Gradle wrapper from the project root:

Windows (PowerShell):
```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
```

macOS / Linux:
```bash
./gradlew assembleDebug
./gradlew installDebug
```

You can also install the generated APK located at `app/build/outputs/apk/debug/app-debug.apk` using `adb install`.

## Notes
- Images can be selected from device storage or captured with camera; the app encodes camera captures as data URIs.
- UI is implemented in `Screens.kt` using Compose; adjust themes in `ui/theme`.

If you'd like, I can also add a CONTRIBUTING section, license, or CI/Gradle run tasks.
