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
