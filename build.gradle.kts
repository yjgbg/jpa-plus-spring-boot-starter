plugins {
    `java-library`
    `maven-publish`
}

group = "com.github.yjgbg"
version = "2.1.3.007-SNAPSHOT"
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
    publications.create<MavenPublication>("snapshot") {
        from(components["java"])
        pom {
            version = "${project.ext["publicationVersion"].toString()}-SNAPSHOT"
        }
    }
    publications.create<MavenPublication>("hypers") {
        from(components["java"])
        pom {
            groupId = "com.hypers.weicl"
            version = project.ext["publicationVersion"].toString()
        }
    }
    repositories.maven("https://oss.sonatype.org/content/repositories/snapshots") {
        name = "snapshot"
        credentials {
            username = project.ext["mavenUsername"].toString()
            password = project.ext["mavenPassword"].toString()
        }
    }
    repositories.maven("https://nexus3.hypers.cc/repository/maven-releases/") {
        name = "hypers"
        credentials {
            username = project.ext["hypersMavenUsername"].toString()
            password = project.ext["hypersMavenPassword"].toString()
        }
    }
}