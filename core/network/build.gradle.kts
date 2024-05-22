import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
}

val localProperties = gradleLocalProperties(rootProject.rootDir, providers)

fun getApiKey(propertyKey: String): String =
    localProperties.getProperty(propertyKey)

android {
    namespace = "com.example.network"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "MARVEL_API_PUBLIC_KEY", getApiKey("MARVEL_API_PUBLIC_KEY"))
            buildConfigField("String", "MARVEL_API_PRIVATE_KEY", getApiKey("MARVEL_API_PRIVATE_KEY"))
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "MARVEL_API_PUBLIC_KEY", getApiKey("MARVEL_API_PUBLIC_KEY"))
            buildConfigField("String", "MARVEL_API_PRIVATE_KEY", getApiKey("MARVEL_API_PRIVATE_KEY"))
        }
    }
}

dependencies {

    implementation(libs.squareup.okhttp3)
    implementation(libs.squareup.okhttp3.logging)

    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.squareup.retrofit2.converter.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(project(":core:security"))
}