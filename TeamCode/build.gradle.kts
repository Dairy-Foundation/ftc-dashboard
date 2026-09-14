plugins {
    id("dev.frozenmilk.teamcode") version "11.2.1-1.2.0"
    id("dev.frozenmilk.sinister.sloth.load") version "0.3.1"
}

ftc {
    kotlin()
    sdk.TeamCode()
    implementation(dairy.Sloth("0.3.1"))
    implementation(dairy.slothboard(""))
}