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
