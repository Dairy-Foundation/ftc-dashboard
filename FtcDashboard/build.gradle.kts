import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
	id("com.github.node-gradle.node") version "2.2.4"
	id("dev.frozenmilk.android-library") version "10.2.0-0.1.3"
	id("dev.frozenmilk.publish") version "0.0.4"
	id("dev.frozenmilk.doc") version "0.0.4"
	id("org.gradle.checkstyle")
}

android.namespace = "com.acmerobotics.dashboard"

checkstyle {
	toolVersion = "8.18"
}

node {
	version = "18.12.1"
	download = true
	nodeModulesDir = file("${project.projectDir}/../client")
}

val yarnBuild = tasks.named("yarn_build")
val yarnInstall = tasks.named("yarn_install")

yarnBuild.dependsOn(yarnInstall)

val cleanDashAssets by tasks.registering(Delete::class) {
	delete("${android.sourceSets.getByName("main").assets.srcDirs.first()}/dash")
}

tasks.named("clean").dependsOn(cleanDashAssets)

val copyDashAssets by tasks.registering(Copy::class) {
	from("${project.projectDir}/../client/dist")
	into("${android.sourceSets.getByName("main").assets.srcDirs.first()}/dash")
}

copyDashAssets.dependsOn(cleanDashAssets)
copyDashAssets.dependsOn(yarnBuild)

android.libraryVariants.all {
	preBuildProvider.get().dependsOn(copyDashAssets)
}

ftc {
	sdk {
		configurationNames = setOf("api")
		RobotCore
		Hardware
		RobotServer
		FtcCommon
	}
}

repositories {
	files("../libs")
	mavenCentral()

	maven {
		name = "dairyReleases"
		url = uri("https://repo.dairy.foundation/releases")
	}
}

dependencies {
	afterEvaluate {
		api("com.acmerobotics.slothboard:core:$version") {
			isTransitive = false
		}
	}

	implementation("dev.frozenmilk.sinister:Sloth:0.2.2")

	implementation("org.nanohttpd:nanohttpd-websocket:2.3.1") {
		exclude(module = "nanohttpd")
	}
}

dairyPublishing {
	gitDir = file("..")
}

publishing {
	publications {
		register<MavenPublication>("release") {
			groupId = "com.acmerobotics.slothboard"
			artifactId = "dashboard"

			artifact(dairyDoc.dokkaHtmlJar)
			artifact(dairyDoc.dokkaJavadocJar)

			afterEvaluate {
				from(components["release"])
			}

			pom {
				description = "Web dashboard designed for FTC"
				name = "FTC Dashboard"
				url = "https://github.com/acmerobotics/ftc-dashboard"

				licenses {
					license {
						name = "The MIT License"
						url = "https://opensource.org/licenses/MIT"
						distribution = "repo"
					}
				}

				developers {
					developer {
						id = "rbrott"
						name = "Ryan Brott"
						email = "rcbrott@gmail.com"
					}
				}

				scm {
					url = "https://github.com/acmerobotics/ftc-dashboard"
				}
			}
		}
	}
}
