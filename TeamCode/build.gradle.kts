plugins {
	id("dev.frozenmilk.teamcode") version "10.3.0-0.1.4"
	id("dev.frozenmilk.sinister.sloth.load") version "0.2.2"
}

ftc {
	// use this to easily add more FTC libraries

	// adds support for kotlin
	kotlin
}

dependencies {
	implementation("com.acmerobotics.slothboard:dashboard")
}