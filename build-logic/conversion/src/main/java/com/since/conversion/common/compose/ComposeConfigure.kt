package com.since.conversion.common.compose

import com.android.build.api.dsl.CommonExtension
import com.since.conversion.common.libs.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.composeConfigure(
    commonExtension: CommonExtension<*,*,*,*,*,*>
){

    commonExtension.apply {
        buildFeatures {
            compose=true
        }
    }


    dependencies {
        val bom = libs.findLibrary("androidx.compose.bom").get()

        "implementation"(platform(bom))
        "androidTestImplementation"(platform(bom))
        "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling.preview").get())

    }

}