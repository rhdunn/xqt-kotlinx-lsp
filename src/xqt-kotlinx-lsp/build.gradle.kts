import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import java.net.URI

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:${Version.Plugin.dokka}")
    }
}

plugins {
    kotlin("multiplatform") version Version.Plugin.kotlinMultiplatform
    kotlin("plugin.serialization") version Version.Plugin.kotlinSerialization
    id("org.jetbrains.dokka") version Version.Plugin.dokka
    id("maven-publish")
    id("signing")
}

group = ProjectMetadata.GitHub.GroupId
version = ProjectMetadata.Build.Version

// region Kotlin Multiplatform (Common)

kotlin.sourceSets {
    commonMain.kotlin.srcDir("commonMain")
    commonTest.kotlin.srcDir("commonTest")

    commonMain.dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.Dependency.kotlinSerialization}")
        implementation("io.github.rhdunn:xqt-kotlinx-json-rpc:${Version.Dependency.xqtJsonRpc}")
    }

    commonTest.dependencies {
        implementation(kotlin("test"))
        implementation(project(":src:xqt-kotlinx-test"))
    }
}

// endregion
// region Kotlin JVM

kotlin.jvm {
    compilations.all {
        kotlinOptions.jvmTarget = BuildConfiguration.jvmTarget(project)
    }

    withJava()

    testRuns["test"].executionTask.configure {
        useJUnitPlatform() // JUnit 5
    }
}

kotlin.sourceSets {
    jvmMain.kotlin.srcDir("jvmMain")
    jvmTest.kotlin.srcDir("jvmTest")
}

// endregion
// region Kotlin JS

rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().download = BuildConfiguration.nodeJsDownload(project)
}

kotlin.js(KotlinJsCompilerType.BOTH).browser {
    testTask {
        useKarma {
            when (BuildConfiguration.jsBrowser(project)) {
                JsBrowser.Chrome -> useChromeHeadless()
                JsBrowser.ChromeCanary -> useChromeCanaryHeadless()
                JsBrowser.Chromium -> useChromiumHeadless()
                JsBrowser.Firefox -> useFirefoxHeadless()
                JsBrowser.FirefoxAurora -> useFirefoxAuroraHeadless()
                JsBrowser.FirefoxDeveloper -> useFirefoxDeveloperHeadless()
                JsBrowser.FirefoxNightly -> useFirefoxNightlyHeadless()
                JsBrowser.PhantomJs -> usePhantomJS()
                JsBrowser.Safari -> useSafari()
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
// region Kotlin Native

when (BuildConfiguration.hostOs(project)) {
    HostOs.Windows -> kotlin.mingwX64("native")
    HostOs.Linux -> kotlin.linuxX64("native")
    HostOs.MacOsX -> kotlin.macosX64("native")
    else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
}

kotlin.sourceSets {
    nativeMain.kotlin.srcDir("nativeMain")
    nativeTest.kotlin.srcDir("nativeTest")
}

// endregion
// region Documentation

tasks.withType<DokkaTask>().configureEach {
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "Copyright © ${ProjectMetadata.Copyright.Year} ${ProjectMetadata.Copyright.Owner}"
    }
}

@Suppress("KDocMissingDocumentation")
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing.publications.withType<MavenPublication> {
    artifact(javadocJar)
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
    maven {
        name = "sonatype"

        url = if (ProjectMetadata.Build.Type == BuildType.Release) {
            URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        } else {
            URI("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }

        credentials {
            username = BuildConfiguration.ossrhUsername(project)
            password = BuildConfiguration.ossrhPassword(project)
        }
    }
}

// endregion
// region Signing Artifacts

signing {
    isRequired = BuildConfiguration.signMavenArtifacts(project)

    useGpgCmd()
}

publishing.publications.configureEach {
    if (signing.isRequired) {
        signing.sign(this)
    }
}

// See https://youtrack.jetbrains.com/issue/KT-46466
// ... Note: This is due to the javadocJar task being shared across all the publications.
@Suppress("KDocMissingDocumentation")
val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(signingTasks)
}

// endregion
