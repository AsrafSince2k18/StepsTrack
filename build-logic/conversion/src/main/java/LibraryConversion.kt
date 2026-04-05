import com.android.build.api.dsl.LibraryExtension
import com.since.conversion.common.applicationAndLibraryConfigure
import com.since.conversion.common.build_type.BuildTypes
import com.since.conversion.common.build_type.buildTypeConfigure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class LibraryConversion: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply{
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension>() {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                applicationAndLibraryConfigure(
                    commonExtension = this
                )
                buildTypeConfigure(
                    buildTypes = BuildTypes.LIBRARY,
                    commonExtension = this
                )
            }

            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }

}