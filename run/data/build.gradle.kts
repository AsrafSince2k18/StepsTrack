plugins {
    alias(libs.plugins.runs.android.library)
    alias(libs.plugins.runs.ktor)
}

android {
    namespace = "com.since.run.data"

}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.run.domain)
    implementation(libs.bundles.koin)


}