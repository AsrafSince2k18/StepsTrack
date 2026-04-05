plugins {
    alias(libs.plugins.runs.jvm)
}


dependencies{

    implementation(projects.core.domain)

    implementation(libs.kotlinx.coroutines.core)
}