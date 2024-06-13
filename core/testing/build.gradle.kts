plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.library.compose)
    alias(libs.plugins.convention.android.hilt)
}

android {
    namespace = "com.example.testing"

//    testOptions.unitTests.isIncludeAndroidResources = true

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {

//    api(libs.androidx.test.runner)
    api(libs.kotlinx.coroutines.test)
    testApi(libs.robolectric)
    testApi(libs.mockito.inline)
    testApi(libs.mockito.kotlin2)

    implementation(libs.androidx.dataStore.preferences)

    implementation(projects.core.shared)
    implementation(projects.core.model)
    implementation(projects.core.database)
    implementation(projects.core.network)
    implementation(projects.core.datastore.preferences)
}