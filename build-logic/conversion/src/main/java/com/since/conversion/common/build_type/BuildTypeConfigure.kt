package com.since.conversion.common.build_type

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.buildTypeConfigure(
    buildTypes: BuildTypes,
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {

    val apiKey = gradleLocalProperties(rootDir,providers).getProperty("API_KEY")

    commonExtension.apply {
        buildFeatures {
            buildConfig = true
        }
    }


    when (buildTypes) {
        BuildTypes.APPLICATION -> {
            extensions.configure<ApplicationExtension>() {
                buildTypes {
                    debug {
                            debugType(apiKey=apiKey)
                    }

                    release {
                        releaseType(
                            commonExtension = commonExtension,
                            apiKey = apiKey
                        )
                    }

                }
            }
        }

        BuildTypes.LIBRARY -> {
            extensions.configure<LibraryExtension>() {
                buildTypes {
                    debug {
                        debugType(apiKey=apiKey)
                    }
                    release {
                        releaseType(
                            commonExtension = commonExtension,
                            apiKey = apiKey
                        )
                    }

                }
            }
        }
    }
}


private fun BuildType.debugType(
    apiKey: String
) {

    buildConfigField(
        type = "String",
        name = "BASE_URL",
        value = "\"https://tracker-2k18s.onrender.com\""
    )

    buildConfigField(
        type = "String",
        name = "API_KEY",
        value = "\"${apiKey}\""
    )

}

private fun BuildType.releaseType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String
) {

    buildConfigField(
        type = "String",
        name = "BASE_URL",
        value = "\"https://tracker-2k18s.onrender.com\""
    )
    buildConfigField(
        type = "String",
        name = "API_KEY",
        value = "\"${apiKey}\""
    )

    isMinifyEnabled = false
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}