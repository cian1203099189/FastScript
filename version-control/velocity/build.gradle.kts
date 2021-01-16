plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
    id("net.kyori.blossom")
}


repositories {
    maven("https://nexus.velocitypowered.com/repository/velocity-artifacts-snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/sonatype-nexus-snapshots/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

blossom {
    replaceTokenIn("src/main/kotlin/me/scoretwo/fastscript/velocity/VelocityPlugin.kt")
    replaceToken("%%version%%", rootProject.version)
    replaceToken("%%description%%", rootProject.description)
}

dependencies {
    implementation(project(":common"))

    compileOnly("com.velocitypowered:velocity-api:1.0.11-SNAPSHOT")
    implementation("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT")
    implementation("me.scoretwo:commons-velocity-plugin:2.0.2-SNAPSHOT")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
        }
    }
}

/*
tasks.processResources {
    from("src/main/resource") {
        include("velocity-plugin.json")
        expand(mapOf(
            "modid" to "fastscript",
            "name" to project.name,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.velocity.VelocityPlugin",
            "version" to project.version,
            "description" to project.description
        ))
    }
}*/