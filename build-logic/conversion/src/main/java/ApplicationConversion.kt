import com.android.build.api.dsl.ApplicationExtension
import com.since.conversion.common.applicationAndLibraryConfigure
import com.since.conversion.common.build_type.BuildTypes
import com.since.conversion.common.build_type.buildTypeConfigure
import com.since.conversion.common.libs.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationConversion : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply{
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }


            extensions.configure<ApplicationExtension>{
                defaultConfig {
                    applicationId=libs.findVersion("applicationId").get().toString()
                    targetSdk=libs.findVersion("targetSdk").get().toString().toInt()
                    versionCode=libs.findVersion("versionCode").get().toString().toInt()
                    versionName=libs.findVersion("versionName").get().toString()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                }

                applicationAndLibraryConfigure(commonExtension = this)

                buildTypeConfigure(
                    buildTypes = BuildTypes.APPLICATION,
                    commonExtension = this
                )
            }
        }
    }
}