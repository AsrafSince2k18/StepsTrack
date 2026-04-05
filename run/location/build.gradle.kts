plugins {
    alias(libs.plugins.runs.android.library)
}

android {
    namespace = "com.since.run.location"

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.run.domain)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.bundles.koin)
}