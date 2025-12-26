plugins {
	id("dev.frozenmilk.jvm-library") version "11.0.0-1.0.0"
	id("dev.frozenmilk.publish") version "0.0.4"
	id("dev.frozenmilk.doc") version "0.0.4"
	id("org.gradle.checkstyle")
}

checkstyle {
	toolVersion = "8.18"
}

dependencies {
	//noinspection NewerVersionAvailable
	implementation("com.google.code.gson:gson:2.8.6")

	testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

	testImplementation("org.nanohttpd:nanohttpd-websocket:2.3.1")
}

dairyPublishing {
	gitDir = file("..")
}

publishing {
	publications {
		register<MavenPublication>("release") {
			groupId = "com.acmerobotics.slothboard"
			artifactId = "core"

			artifact(dairyDoc.dokkaHtmlJar)
			artifact(dairyDoc.dokkaJavadocJar)

			afterEvaluate {
				from(components["java"])
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
