plugins {
    alias(libs.plugins.convention.android.feature)
    alias(libs.plugins.convention.android.library.compose)
}

android {
    namespace = "com.example.home"

    defaultConfig {
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
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    implementation(libs.bumptech.glide.compose)
    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.work.ktx)

    implementation(libs.androidx.paging.testing)

    /*Module*/
    implementation(projects.work)
}