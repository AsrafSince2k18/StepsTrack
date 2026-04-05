import com.android.build.api.dsl.ApplicationExtension
import com.since.conversion.common.compose.composeConfigure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class ApplicationComposeConversion : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply{
                apply("run.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extensions=extensions.getByType<ApplicationExtension>()
            composeConfigure(commonExtension = extensions)

        }
    }

}