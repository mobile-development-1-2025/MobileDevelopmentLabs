plugins {
    id("android-library-convention")
    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dak0ta.learnity.core.network.data"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.core.datastore.domain)
    api(projects.core.network.domain)

    api(libs.logging.interceptor)
    api(libs.moshi)
    api(libs.retrofit)

    implementation(projects.core.coroutine)
    implementation(projects.core.di)

    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp)
    implementation(libs.retrofit.converter.moshi)

    kapt(libs.dagger.compiler)
    ksp(libs.moshi.kotlin.codegen)
}
