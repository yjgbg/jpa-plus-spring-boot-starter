plugins {
    `java-library`
    `maven-publish`
}

group = "com.github.yjgbg"
version = "hypers-2.5.5-SNAPSHOT"
description = "jpa-plus-spring-boot-starter"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}


dependencies {
    api("com.github.yjgbg:active-entity:1.3")
    api("com.google.guava:guava:30.1.1-jre")
    api("org.springframework.boot:spring-boot-starter-data-jpa:2.5.5")
    compileOnly("org.jetbrains:annotations:+")
    compileOnly("org.projectlombok:lombok:+")
    annotationProcessor("org.projectlombok:lombok:+")

}

java {
    withSourcesJar()
}

publishing {
    publications.create<MavenPublication>("this") {
        from(components["java"])
    }
    repositories.maven("https://oss.sonatype.org/content/repositories/snapshots") {
        credentials {
            username = project.ext["mavenUsername"].toString()
            password = project.ext["mavenPassword"].toString()
        }
    }
}