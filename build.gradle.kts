// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    // alias(libs.plugins.google.gms.google.services) apply false
}
buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin.v201)
        classpath (libs.google.secrets.gradle.plugin)
    }
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/google/secrets-gradle-plugin")
            credentials {
                username = project.findProperty("Shiwal25") as String? ?: System.getenv("Shiwal25")
                password = project.findProperty("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") as String? ?: System.getenv("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")
            }
        }
    }
}
