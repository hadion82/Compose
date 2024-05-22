package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Logging options
 */
internal fun Project.configureLogging(
) {
    dependencies {
        add("implementation", libs.findLibrary("timber").get())
    }
}