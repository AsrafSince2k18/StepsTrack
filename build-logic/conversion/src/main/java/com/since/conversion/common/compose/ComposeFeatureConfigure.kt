package com.since.conversion.common.compose

import com.since.conversion.common.libs.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

internal fun DependencyHandlerScope.composeFeatureConfigure(project: Project){

    "implementation"(project(":core:presentaction:designsystem"))
    "implementation"(project(":core:presentaction:ui"))

    "implementation"(project.libs.findBundle("compose").get())
    "debugImplementation"(project.libs.findBundle("compose.debug").get())
    "implementation"(project.libs.findBundle("koin.compose").get())
    "androidTestImplementation"(project.libs.findLibrary("androidx.compose.ui.test.junit4").get())
}
