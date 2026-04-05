import com.android.build.api.dsl.LibraryExtension
import com.since.conversion.common.compose.composeConfigure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class LibraryApplicationCompose : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply{
                apply("run.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            val extensions=extensions.getByType<LibraryExtension>()
            composeConfigure(commonExtension = extensions)

        }
    }

}