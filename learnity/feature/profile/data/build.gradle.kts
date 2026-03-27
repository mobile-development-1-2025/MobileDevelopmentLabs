plugins {
    id("jvm-convention")
}

dependencies {
    implementation(projects.feature.profile.domain)

    implementation(projects.core.coroutine)
    implementation(projects.core.database.domain)
    implementation(projects.core.datastore.domain)
    implementation(projects.core.di)
    implementation(projects.core.network.domain)
}
