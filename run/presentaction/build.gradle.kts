plugins {
    alias(libs.plugins.runs.android.library.compose.feature)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

android {
    namespace = "com.since.run.presentaction"

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.run.domain)
    implementation(libs.google.maps.android.compose)
}