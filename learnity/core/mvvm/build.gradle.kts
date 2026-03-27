plugins {
    id("android-library-compose-convention")
}

android {
    namespace = "com.dak0ta.learnity.core.mvvm"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel)
}
