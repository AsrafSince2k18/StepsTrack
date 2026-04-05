import com.since.conversion.common.kotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmConversion : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            kotlinJvm()

        }
    }

}