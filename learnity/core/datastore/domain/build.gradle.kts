plugins {
    id("jvm-convention")
}

dependencies {
    api(projects.core.domain)

    implementation(projects.core.coroutine)
}
