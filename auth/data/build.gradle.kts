plugins {
    alias(libs.plugins.runs.android.library)
    alias(libs.plugins.runs.ktor)
}

android {
    namespace = "com.since.auth.data"

}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.auth.domain)

    //koin
    implementation(libs.bundles.koin)

}