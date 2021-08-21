plugins {
    kotlin("js") version "1.5.0"
}

group = "me.hfd"
version = "1.0"
val kotlinVersion = "1.5.0"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js:$kotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromium()
                }
            }
        }
    }
}