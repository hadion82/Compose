pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Compose"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core")
include(":core:data")
include(":core:domain")
include(":core:shared")
include(":core:security")
include(":core:ui")
include(":core:model")
include(":feature")
include(":feature:home")
include(":feature:bookmarks")
include(":core:database")
include(":core:network")
include(":navigator")
include(":lint")
include(":core:notifications")
include(":core:datastore")
include(":core:datastore:preferences")
include(":core:datastore:proto")

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
include(":feature:profile")
include(":work")
include(":core:design")
include(":benchmark")
include(":core:testing")
include(":core:test")
include(":core:test:domain-test")
include(":core:test:data-test")
include(":core:fio")
include(":core:test:fio-test")
