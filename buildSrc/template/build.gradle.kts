// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
@file:Suppress("KDocMissingDocumentation")

import io.github.rhdunn.gradle.dsl.*
import io.github.rhdunn.gradle.js.KarmaBrowserTarget
import io.github.rhdunn.gradle.maven.ArtifactSigningMethod
import io.github.rhdunn.gradle.maven.BuildType
import io.github.rhdunn.gradle.maven.MavenSonatype
import io.github.rhdunn.gradle.maven.SupportedVariants
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.konan.target.KonanTarget
import java.net.URI

buildscript {
    dependencies {
        classpath(Dependency.DokkaBase)
    }
}

plugins {
    kotlin("multiplatform") version Version.Plugin.KotlinMultiplatform
    id("org.jetbrains.dokka") version Version.Plugin.Dokka
    id("maven-publish")
    id("signing")
}

group = ProjectMetadata.GitHub.GroupId
version = ProjectMetadata.Build.Version

// region Kotlin Multiplatform (Common)

kotlin.sourceSets {
    commonMain.kotlin.srcDir("commonMain")
    commonTest.kotlin.srcDir("commonTest")

    commonTest.dependencies {
        implementation(kotlin("test"))
    }
}

// endregion
// region Kotlin JS

rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().download = BuildConfiguration.nodeJsDownload(project)
}

kotlin.js(KotlinJsCompilerType.BOTH).browser {
    testTask {
        useKarma {
            when (BuildConfiguration.karmaBrowserTarget(project)) {
                KarmaBrowserTarget.Chrome -> useChrome()
                KarmaBrowserTarget.ChromeHeadless -> useChromeHeadless()
                KarmaBrowserTarget.ChromeCanary -> useChromeCanary()
                KarmaBrowserTarget.ChromeCanaryHeadless -> useChromeCanaryHeadless()
                KarmaBrowserTarget.Chromium -> useChromium()
                KarmaBrowserTarget.ChromiumHeadless -> useChromiumHeadless()
                KarmaBrowserTarget.Firefox -> useFirefox()
                KarmaBrowserTarget.FirefoxHeadless -> useFirefoxHeadless()
                KarmaBrowserTarget.FirefoxAurora -> useFirefoxAurora()
                KarmaBrowserTarget.FirefoxAuroraHeadless -> useFirefoxAuroraHeadless()
                KarmaBrowserTarget.FirefoxDeveloper -> useFirefoxDeveloper()
                KarmaBrowserTarget.FirefoxDeveloperHeadless -> useFirefoxDeveloperHeadless()
                KarmaBrowserTarget.FirefoxNightly -> useFirefoxNightly()
                KarmaBrowserTarget.FirefoxNightlyHeadless -> useFirefoxNightlyHeadless()
                KarmaBrowserTarget.PhantomJs -> usePhantomJS()
                KarmaBrowserTarget.Safari -> useSafari()
                KarmaBrowserTarget.Opera -> useOpera()
                KarmaBrowserTarget.Ie -> useIe()
            }
        }
    }
}

kotlin.js(KotlinJsCompilerType.BOTH).nodejs {
}

kotlin.sourceSets {
    jsMain.kotlin.srcDir("jsMain")
    jsTest.kotlin.srcDir("jsTest")
}

// endregion
// region Kotlin JVM

val supportedJvmVariants = BuildConfiguration.jvmVariants(project)
val javaVersion = BuildConfiguration.javaVersion(project)

lateinit var javaTarget: KotlinJvmTarget
ProjectMetadata.BuildTargets.JvmTargets.forEach { jvmTarget ->
    val jvmName = supportedJvmVariants.jvmPublication(jvmTarget, javaVersion) ?: return@forEach
    val target: KotlinJvmTarget = kotlin.jvm(jvmName) {
        compilations.all {
            kotlinOptions.jvmTarget = jvmTarget.toString()
        }

        if (jvmTarget == javaVersion) {
            withJava()
        }

        attributes {
            attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, jvmTarget.majorVersion.toInt())
        }

        testRuns["test"].executionTask.configure {
            useJUnitPlatform() // JUnit 5
        }
    }

    if (jvmTarget == javaVersion)
        javaTarget = target
}

if (supportedJvmVariants !== SupportedVariants.None) {
    kotlin.sourceSets {
        jvmMain(javaTarget).kotlin.srcDir("jvmMain")
        jvmTest(javaTarget).kotlin.srcDir("jvmTest")
    }
}

// endregion
// region Kotlin Native

val supportedKonanVariants = BuildConfiguration.konanVariants(project)
val konanBuildTarget = BuildConfiguration.konanTarget(project)

lateinit var nativeTarget: KotlinNativeTarget
ProjectMetadata.BuildTargets.KonanTargets.forEach { konanTarget ->
    val nativeName = supportedKonanVariants.nativePublication(konanTarget, konanBuildTarget) ?: return@forEach

    // https://kotlinlang.org/docs/native-target-support.html
    val target = when (konanTarget) {
        KonanTarget.ANDROID_ARM32 -> kotlin.androidNativeArm32(nativeName) // Tier 3
        KonanTarget.ANDROID_ARM64 -> kotlin.androidNativeArm64(nativeName) // Tier 3
        KonanTarget.ANDROID_X64 -> kotlin.androidNativeX64(nativeName) // Tier 3
        KonanTarget.ANDROID_X86 -> kotlin.androidNativeX86(nativeName) // Tier 3
        KonanTarget.IOS_ARM32 -> kotlin.iosArm32(nativeName) // Deprecated, to be removed in 1.9.20
        KonanTarget.IOS_ARM64 -> kotlin.iosArm64(nativeName) // Tier 2
        KonanTarget.IOS_SIMULATOR_ARM64 -> kotlin.iosSimulatorArm64(nativeName) // Tier 1
        KonanTarget.IOS_X64 -> kotlin.iosX64(nativeName) // Tier 1
        KonanTarget.LINUX_ARM32_HFP -> kotlin.linuxArm32Hfp(nativeName) // Deprecated, to be removed in 1.9.20
        KonanTarget.LINUX_ARM64 -> kotlin.linuxArm64(nativeName) // Tier 2
        KonanTarget.LINUX_MIPS32 -> kotlin.linuxMips32(nativeName) // Deprecated, to be removed in 1.9.20
        KonanTarget.LINUX_MIPSEL32 -> kotlin.linuxMipsel32(nativeName) // Deprecated, to be removed in 1.9.20
        KonanTarget.LINUX_X64 -> kotlin.linuxX64(nativeName) // Tier 1 ; native host
        KonanTarget.MACOS_ARM64 -> kotlin.macosArm64(nativeName) // Tier 1 ; native host
        KonanTarget.MACOS_X64 -> kotlin.macosX64(nativeName) // Tier 1 ; native host
        KonanTarget.MINGW_X64 -> kotlin.mingwX64(nativeName) // Tier 3 ; native host
        KonanTarget.MINGW_X86 -> kotlin.mingwX86(nativeName) // Deprecated, to be removed in 1.9.20
        KonanTarget.TVOS_ARM64 -> kotlin.tvosArm64(nativeName) // Tier 2
        KonanTarget.TVOS_SIMULATOR_ARM64 -> kotlin.tvosSimulatorArm64(nativeName) // Tier 2
        KonanTarget.TVOS_X64 -> kotlin.tvosX64(nativeName) // Tier 2
        KonanTarget.WASM32 -> kotlin.wasm32(nativeName) // Deprecated, to be removed in 1.9.20
        KonanTarget.WATCHOS_ARM32 -> kotlin.watchosArm32(nativeName) // Tier 2
        KonanTarget.WATCHOS_ARM64 -> kotlin.watchosArm64(nativeName) // Tier 2
        KonanTarget.WATCHOS_SIMULATOR_ARM64 -> kotlin.watchosSimulatorArm64(nativeName) // Tier 2
        KonanTarget.WATCHOS_X64 -> kotlin.watchosX64(nativeName) // Tier 2
        KonanTarget.WATCHOS_X86 -> kotlin.watchosX86(nativeName) // Deprecated, to be removed in 1.9.20
        is KonanTarget.ZEPHYR -> throw GradleException("Kotlin/Native build target 'zephyr' is not supported.")
    }

    if (konanTarget == konanBuildTarget)
        nativeTarget = target
}

if (supportedKonanVariants !== SupportedVariants.None) {
    kotlin.sourceSets {
        nativeMain(nativeTarget).kotlin.srcDir("nativeMain")
        nativeTest(nativeTarget).kotlin.srcDir("nativeTest")
    }
}

// endregion
// region Dokka

tasks.withType<DokkaTaskPartial>().configureEach {
    dokkaSourceSets.configureEach {
        includes.from(file("README.md"))
    }

    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "Copyright © ${ProjectMetadata.Copyright.Year} ${ProjectMetadata.Copyright.Owner}"
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets.configureEach {
        includes.from(file("README.md"))
    }

    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "Copyright © ${ProjectMetadata.Copyright.Year} ${ProjectMetadata.Copyright.Owner}"
    }
}

// endregion
// region Maven POM Metadata

publishing.publications.withType<MavenPublication> {
    pom {
        name.set("$groupId:$artifactId")
        description.set(ProjectMetadata.Description)
        url.set(ProjectMetadata.GitHub.ProjectUrl)

        licenses {
            license {
                name.set(ProjectMetadata.License.Name)
                url.set(ProjectMetadata.License.Url)
                distribution.set("repo")
            }
        }

        developers {
            developer {
                name.set(ProjectMetadata.GitHub.AccountName)
                email.set(ProjectMetadata.GitHub.AccountEmail)
                organization.set(ProjectMetadata.GitHub.AccountId)
                organizationUrl.set(ProjectMetadata.GitHub.AccountUrl)
            }
        }

        scm {
            connection.set("scm:git:${ProjectMetadata.GitHub.CloneSshUrl}")
            developerConnection.set("scm:git:${ProjectMetadata.GitHub.CloneSshUrl}")
            url.set(ProjectMetadata.GitHub.ProjectUrl)

            if (ProjectMetadata.Build.Type == BuildType.Release) {
                tag.set(ProjectMetadata.Build.VersionTag)
            }
        }

        issueManagement {
            url.set(ProjectMetadata.GitHub.IssuesUrl)
        }
    }
}

// endregion
// region Publish to Maven

publishing.repositories {
    val sonatype = BuildConfiguration.mavenRepositorySonatype(project)
    if (sonatype != MavenSonatype.None) {
        maven {
            name = "MavenCentral"

            url = URI(sonatype.url!!)

            credentials {
                username = BuildConfiguration.ossrhUsername(project)
                password = BuildConfiguration.ossrhPassword(project)
            }
        }
    }
}

// endregion
// region Javadoc JAR Files

publishing.publications.withType<MavenPublication> {
    val publication = this

    val javadocJar = tasks.register("${publication.name}JavadocJar", Jar::class) {
        archiveClassifier.set("javadoc")

        // Each archive name should be distinct. Mirror the names for the sources Jar tasks.
        archiveBaseName.set("${archiveBaseName.get()}-${publication.name}")
    }

    artifact(javadocJar)
}

// endregion
// region Signing Artifacts

signing {
    when (BuildConfiguration.mavenSignArtifacts(project)) {
        ArtifactSigningMethod.GpgCommand -> {
            isRequired = true
            useGpgCmd()
        }

        ArtifactSigningMethod.Environment -> {
            val signingKeyId = BuildConfiguration.mavenSigningKeyId(project)
            val signingKeyPrivate = BuildConfiguration.mavenSigningKeyPrivate(project)
            val signingKeyPassword = BuildConfiguration.mavenSigningKeyPassword(project)

            isRequired = !signingKeyPrivate.isNullOrBlank()
            useInMemoryPgpKeys(signingKeyId, signingKeyPrivate, signingKeyPassword)
        }

        ArtifactSigningMethod.None -> {
            isRequired = false
        }
    }
}

publishing.publications.configureEach {
    if (signing.isRequired) {
        signing.sign(this)
    }
}

// endregion
