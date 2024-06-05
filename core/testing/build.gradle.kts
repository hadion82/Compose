plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.library.compose)
    alias(libs.plugins.convention.android.hilt)
}

android {
    namespace = "com.example.testing"

    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {

//    api(libs.androidx.test.runner)
    api(libs.kotlinx.coroutines.test)
    api(libs.robolectric)
    api(libs.mockito.inline)
    api(libs.mockito.kotlin2)
    api(libs.hamcrest)

    implementation(projects.core.shared)
    implementation(projects.core.model)
    implementation(projects.core.database)
    implementation(projects.core.network)
}