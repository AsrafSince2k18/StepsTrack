plugins {
    alias(libs.plugins.runs.android.library)
    alias(libs.plugins.runs.ktor)
}

android {
    namespace = "com.since.core.data"

}

dependencies {

    implementation(projects.core.domain)
    //koin
    implementation(libs.bundles.koin)
    //timber
    implementation(libs.timber)

}