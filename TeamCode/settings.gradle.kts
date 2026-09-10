pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
		maven("https://repo.dairy.foundation/releases")
	}
}

includeBuild("../FtcDashboard") {
	dependencySubstitution {
		substitute(module("com.acmerobotics.slothboard:dashboard")).using(project(":"))
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}
