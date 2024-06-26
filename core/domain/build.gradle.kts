@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.convention.android.library.jacoco)
}

android {
    namespace = "com.example.domain"

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
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.commons.io)

    testImplementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.robolectric)

    /*Module*/
    implementation(projects.core.model)
    implementation(projects.core.shared)
    implementation(projects.core.data)
    implementation(projects.core.test.dataTest)

    implementation(projects.core.testing)
}