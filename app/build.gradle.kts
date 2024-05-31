import com.example.convention.ApplicationBuildType

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.application.jacoco)
}

android {
    namespace = "com.example.compose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.compose"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ApplicationBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.hilt.ext.work)

    /*Module*/
    implementation(projects.feature.home)
    implementation(projects.feature.bookmarks)
    implementation(projects.feature.profile)
    implementation(projects.work)
}