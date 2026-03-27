plugins {
    id("android-library-compose-convention")
}

android {
    namespace = "com.dak0ta.learnity.feature.settings.presentation"
}

dependencies {
    implementation(projects.feature.settings.domain)

    implementation(projects.core.coroutine)
    implementation(projects.core.datastore.domain)
    implementation(projects.core.design)
    implementation(projects.core.di)
    implementation(projects.core.domain)
    implementation(projects.core.mvvm)
    implementation(projects.core.navigationCompose)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
}
