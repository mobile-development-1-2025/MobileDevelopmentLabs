import com.android.build.api.variant.impl.VariantOutputImpl

plugins {
    id("android-application-convention")
    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

val vrsCode: String by project
val vrsName: String by project
val appNameFile: String by project

android {
    namespace = "com.dak0ta.learnity.app"
}

androidComponents {
    onVariants { variant ->
        variant.outputs.filterIsInstance<VariantOutputImpl>().forEach { output ->
            output.outputFileName.set(
                "$appNameFile-${variant.buildType}-$vrsName-$vrsCode.apk",
            )
        }
    }
}

dependencies {
    implementation(projects.feature.authorization.data)
    implementation(projects.feature.authorization.domain)
    implementation(projects.feature.authorization.presentation)
    implementation(projects.feature.home.data)
    implementation(projects.feature.home.domain)
    implementation(projects.feature.home.presentation)
    implementation(projects.feature.profile.data)
    implementation(projects.feature.profile.domain)
    implementation(projects.feature.profile.presentation)
    implementation(projects.feature.settings.domain)
    implementation(projects.feature.settings.presentation)

    implementation(projects.core.coroutine)
    implementation(projects.core.database.data)
    implementation(projects.core.database.domain)
    implementation(projects.core.datastore.data)
    implementation(projects.core.datastore.domain)
    implementation(projects.core.di)
    implementation(projects.core.domain)
    implementation(projects.core.mvvm)
    implementation(projects.core.navigation)
    implementation(projects.core.navigationCompose)
    implementation(projects.core.network.data)
    implementation(projects.core.network.domain)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.work.runtime.ktx)

    kapt(libs.dagger.compiler)
    ksp(libs.moshi.kotlin.codegen)
}
