plugins {
    id("android-library-convention")
    id("kotlin-kapt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.dak0ta.learnity.core.datastore.data"
}

protobuf {
    protoc { artifact = libs.protobuf.protoc.get().toString() }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    api(libs.androidx.datastore)
    api(libs.google.protobuf.javalite)

    implementation(projects.core.coroutine)
    implementation(projects.core.datastore.domain)
    implementation(projects.core.di)

    kapt(libs.dagger.compiler)
}
