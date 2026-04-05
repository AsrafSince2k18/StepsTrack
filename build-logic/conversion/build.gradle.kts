plugins {
    `kotlin-dsl`
}


group = "com.since.buildlogic"


dependencies {
    compileOnly(libs.gradle.gradlePlugin)
    compileOnly(libs.gradle.kotlin.gradlePlugin)
    compileOnly(libs.gradle.tools.common)
}
gradlePlugin {
    plugins {
        create("androidApplication") {
            implementationClass = "ApplicationConversion"
            id = "run.android.application"
        }

        create("androidApplicationCompose") {
            implementationClass = "ApplicationComposeConversion"
            id = "run.android.application.compose"
        }

        create("androidLibrary") {
            implementationClass = "LibraryConversion"
            id = "run.android.library"
        }

        create("androidLibraryCompose") {
            implementationClass = "LibraryApplicationCompose"
            id = "run.android.library.compose"
        }

        create("androidLibraryComposeFeature") {
            implementationClass = "LibraryApplicationFeatureCompose"
            id = "run.android.library.compose.feature"
        }

        create("jvm") {
            implementationClass = "JvmConversion"
            id = "run.jvm"
        }


        create("ktor") {
            implementationClass = "KtorConversion"
            id = "run.ktor"
        }

    }
}