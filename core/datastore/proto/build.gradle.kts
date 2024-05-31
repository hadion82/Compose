import com.google.protobuf.gradle.GenerateProtoTask
import dagger.hilt.android.plugin.util.capitalize

plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.convention.android.library.jacoco)
}

android {
    namespace = "com.example.datastore.proto"
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

/**
 * After the Proto Class is created, a dependency on task order must be created for the Hilt Module to be implemented.
 * For https://github.com/google/ksp/issues/1590
 */

androidComponents.onVariants { variant ->
    afterEvaluate {
        tasks.getByName("ksp${variant.name.capitalize()}Kotlin") {
            val protoTask =
                tasks.getByName("generate${variant.name.capitalize()}Proto") as GenerateProtoTask
            dependsOn(protoTask)
            (this as org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool<*>)
                .setSource(protoTask.outputBaseDir)
        }
    }
}

//androidComponents {
//    onVariants(selector().all(), { variant ->
//        afterEvaluate {
//            val capName = variant.name.capitalize()
//            tasks.getByName("ksp${capName}Kotlin") {
//                setSource(tasks.getByName("generate${capName}Proto").outputs)
//            }
//        }
//    })
//}

dependencies {
    api(libs.protobuf.kotlin.lite)
    implementation(libs.androidx.dataStore.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    /*Module*/
    implementation(projects.core.shared)
    implementation(projects.core.model)
}