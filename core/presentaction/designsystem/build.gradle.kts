plugins {
    alias(libs.plugins.runs.android.library.compose)
}

android {
    namespace = "com.since.presentaction.designsystem"
}

dependencies {

    implementation(projects.core.domain)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    debugImplementation(libs.androidx.compose.ui.tooling)
    api(libs.androidx.core.ktx)


}