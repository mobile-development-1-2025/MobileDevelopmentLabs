plugins {
    id("android-library-convention")
    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dak0ta.learnity.core.database.data"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

dependencies {
    api(projects.core.database.domain)

    implementation(projects.core.coroutine)
    implementation(projects.core.di)

    implementation(libs.androidx.room.runtime)
    implementation(libs.moshi)

    kapt(libs.dagger.compiler)
    ksp(libs.androidx.room.compiler)
    ksp(libs.moshi.kotlin.codegen)
}
