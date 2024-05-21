import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            properties["MARVEL_API_PUBLIC_KEY"]
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

fun getApiKey(propertyKey: String): String =
    gradleLocalProperties(rootProject.rootDir, providers).getProperty(propertyKey)

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)

//    implementation(libs.squareup.javapoet)
//    ksp(libs.dagger.hilt.compiler)
//    ksp(libs.androidx.hilt.compiler)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.squareup.retrofit2.converter.gson)

    implementation(libs.bumptech.glide)
    implementation(libs.commons.io)
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(project(":shared"))
    implementation(project(":security"))
}