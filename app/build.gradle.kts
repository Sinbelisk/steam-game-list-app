import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}


android {
    namespace = "com.example.unplayedgameslist"
    compileSdk = 35

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.unplayedgameslist"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Dependencias estándar
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.picasso)
    implementation(libs.androidx.recyclerview)

    // Dependencias para Room
    implementation(libs.androidx.room.runtime)   // Room runtime
    implementation(libs.androidx.room.ktx)       // Room KTX (opcional, pero recomendado para Kotlin)

    // Dependencia de KSP para Room (esta es la parte nueva)
    ksp(libs.androidx.room.compiler)   // Room KSP compiler

    //Dependencias de retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //coroutinas
    implementation(libs.androidx.lifecycle.viewmodel.ktx)  // Para viewModelScope
    implementation(libs.kotlinx.coroutines.android)  // Para Kotlin Coroutines

    // libreria para encriptar datos
    implementation(libs.androidx.security.crypto)

    //implementacion de glide
    implementation(libs.glide)
    ksp(libs.compiler)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
