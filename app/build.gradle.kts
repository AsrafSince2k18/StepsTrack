plugins {
    alias(libs.plugins.runs.android.compose)
    alias(libs.plugins.runs.ktor)
}

android {
    namespace = "com.since.stepstracker"

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //koin
    implementation(libs.bundles.koin)
    //timber
    implementation(libs.timber)
    //navigation
    implementation(libs.androidx.navigation.compose)
    //sharedPrefrence
    implementation(libs.androidx.security.crypto.ktx)
    //splash
    implementation(libs.androidx.splashscreen)


    //module
    implementation(projects.auth.data)
    implementation(projects.auth.domain)
    implementation(projects.auth.presentaction)

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentaction.designsystem)
    implementation(projects.core.presentaction.ui)

    implementation(projects.run.data)
    implementation(projects.run.domain)
    implementation(projects.run.location)
    implementation(projects.run.presentaction)


}