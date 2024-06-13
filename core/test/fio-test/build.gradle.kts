plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.example.test.fio"

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

    implementation(projects.core.shared)
    implementation(projects.core.fio)
}