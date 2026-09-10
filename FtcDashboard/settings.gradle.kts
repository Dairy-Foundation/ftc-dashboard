pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
		maven("https://repo.dairy.foundation/releases")
	}
}

includeBuild("../DashboardCore") {
	dependencySubstitution {
		substitute(module("com.acmerobotics.slothboard:core")).using(project(":"))
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}
