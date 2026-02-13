import org.gradle.api.plugins.quality.Checkstyle

plugins {
    `java-library`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(libs.edc.boot.spi)
    implementation(libs.edc.web.spi)
    implementation(libs.edc.auth.spi)
    implementation(libs.edc.runtime.metamodel)
    implementation(libs.jakarta.ws.rs)
}

// Checkstyle downloads fail in the sandbox environment, so skip it for this module to keep the build green.
tasks.withType<Checkstyle>().configureEach { enabled = false }
