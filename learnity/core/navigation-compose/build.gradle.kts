plugins {
    id("android-library-compose-convention")
}

android {
    namespace = "com.dak0ta.learnity.core.navigation.compose"
}

dependencies {
    api(projects.core.navigation)

    api(libs.androidx.navigation.compose)
}
