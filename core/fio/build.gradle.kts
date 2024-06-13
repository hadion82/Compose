plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.android.library.jacoco)
}

android {
    namespace = "com.example.fio"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(libs.bumptech.glide)
    implementation(libs.commons.io)
    implementation(libs.timber)

    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(projects.core.shared)
}