plugins {
    alias(libs.plugins.runs.android.library.compose.feature)
}

android {
    namespace = "com.since.presentaction"

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.auth.domain)




}