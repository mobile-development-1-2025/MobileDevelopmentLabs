plugins {
    id("android-library-convention")
    id("kotlin-kapt")
}

android {
    namespace = "com.dak0ta.learnity.feature.home.data"
}

dependencies {
    implementation(projects.feature.home.domain)

    implementation(projects.core.coroutine)
    implementation(projects.core.database.domain)
    implementation(projects.core.datastore.domain)
    implementation(projects.core.di)
    implementation(projects.core.network.domain)

    implementation(libs.androidx.work.runtime.ktx)

    kapt(libs.dagger.compiler)
}
