plugins {
    id("dev.frozenmilk.teamcode") version "11.0.0-1.0.0"
    id("dev.frozenmilk.sinister.sloth.load") version "0.2.4"
}

ftc {
    kotlin()
    sdk.TeamCode()
    implementation(dairy.Sloth)
    implementation(dairy.slothboard)
}