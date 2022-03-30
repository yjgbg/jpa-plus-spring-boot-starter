plugins {
    `java-library`
    `maven-publish`
}

group = "com.github.yjgbg"
version = "2.1.3.008-SNAPSHOT"
description = "jpa-plus-spring-boot-starter"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}


dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa:2.1.3.RELEASE")
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