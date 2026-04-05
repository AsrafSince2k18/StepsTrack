import com.since.conversion.common.libs.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KtorConversion: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply{
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                "implementation"(libs.findBundle("ktor").get())
            }

        }
    }

}