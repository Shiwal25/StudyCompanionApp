plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    //   alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.studycompanion"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.studycompanion"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField ("String", "apiKey", "\"AIzaSyD4fYnKWaESh2CBYepQOOgjkT-Fh_JQ2GM\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.lottie)
    implementation(libs.generativeai)
    implementation(libs.guava)
    implementation(libs.reactive.streams)
}