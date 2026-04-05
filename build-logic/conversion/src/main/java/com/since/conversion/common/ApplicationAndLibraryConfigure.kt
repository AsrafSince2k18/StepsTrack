package com.since.conversion.common

import com.android.build.api.dsl.CommonExtension
import com.since.conversion.common.libs.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.applicationAndLibraryConfigure(
    commonExtension: CommonExtension<*,*,*,*,*,*>
) {

    commonExtension.apply {
        compileSdk=libs.findVersion("compileSdk").get().toString().toInt()
        defaultConfig.minSdk =libs.findVersion("minSdk").get().toString().toInt()


        compileOptions {
            isCoreLibraryDesugaringEnabled=true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlinConfigure()

    }

    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("desugar.jdk.libs").get())
    }


}


private fun Project.kotlinConfigure(){
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}


internal fun Project.kotlinJvm(){

    extensions.configure<JavaPluginExtension>(){
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinConfigure()

}