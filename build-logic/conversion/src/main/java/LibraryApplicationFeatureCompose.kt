import com.since.conversion.common.compose.composeFeatureConfigure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class LibraryApplicationFeatureCompose : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("run.android.library.compose")


            dependencies {
                composeFeatureConfigure(project = target)
            }

        }
    }

}