plugins {
    id("android-library-compose-convention")
}

android {
    namespace = "com.dak0ta.learnity.feature.authorization.presentation"
}

dependencies {
    implementation(projects.feature.authorization.domain)
    implementation(projects.feature.profile.domain)

    implementation(projects.core.coroutine)
    implementation(projects.core.design)
    implementation(projects.core.datastore.domain)
    implementation(projects.core.di)
    implementation(projects.core.mvvm)
    implementation(projects.core.navigationCompose)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
}
