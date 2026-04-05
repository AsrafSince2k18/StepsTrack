plugins {
    alias(libs.plugins.runs.android.library.compose)
}

android {
    namespace = "com.since.presentaction.ui"

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.presentaction.designsystem)

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

}