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
