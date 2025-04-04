// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    //Hilt
    id("com.google.dagger.hilt.android") version "2.46.1" apply false

    id ("com.android.library") version "8.1.4" apply false
}