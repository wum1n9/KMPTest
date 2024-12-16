import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.maven.publish)
}

publishing {
    publications {
        create<MavenPublication>("github") {
            from(components["kotlin"])  // 选择发布组件
            artifactId = "gurusdk"      // 替换为 SDK 名称
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/wum1n9/KMPTest")
            credentials {
                username = "wum1n9"
                password = "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"
            }
        }
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
        }
    }
}

android {
    namespace = "org.example.project.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
