pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "StepsTracker"


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

include(":auth:data")
include(":auth:domain")
include(":auth:presentaction")

include(":core:data")
include(":core:domain")
include(":core:presentaction:designsystem")
include(":core:presentaction:ui")

include(":run:data")
include(":run:location")
include(":run:domain")
include(":run:presentaction")

