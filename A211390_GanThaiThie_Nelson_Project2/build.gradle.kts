// Top-level build file where you can add configuration options common to all sub-projects/modules.
extra["room_version"] = "2.7.0"

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.4" apply false
}