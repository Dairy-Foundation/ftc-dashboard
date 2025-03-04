plugins {
	id("dev.frozenmilk.teamcode") version "10.2.0-0.1.3"
	id("dev.frozenmilk.sinister.sloth.load") version "0.1.0"
}

ftc {
	// use this to easily add more FTC libraries

	// adds support for kotlin
	kotlin
}

dependencies {
	implementation("com.acmerobotics.slothboard:dashboard")
}