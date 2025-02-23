// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id ("androidx.navigation.safeargs") version "2.8.7" apply false
    kotlin("jvm") version "2.1.10"
    id("com.google.devtools.ksp") version "2.1.10-1.0.30" apply false
}