@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.convention.android.room)
}

android {
    namespace = "com.example.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.squareup.retrofit2.converter.gson)

    implementation(libs.bumptech.glide)
    implementation(libs.commons.io)
    implementation(libs.timber)

    testImplementation(libs.androidx.paging.testing)

    /*Module*/
    implementation(projects.core.shared)
    implementation(projects.core.database)
    implementation(projects.core.network)
    implementation(projects.core.security)
    implementation(projects.core.datastore.preferences)
    implementation(projects.core.testing)
}