plugins {
    `java-library`
    `maven-publish`
}

group = "com.github.yjgbg"
version = "2.7.0"
description = "jpa-plus-spring-boot-starter"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}


dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa:2.7.0")
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

java {
    withSourcesJar()
}


publishing {
    publications.create<MavenPublication>("snapshot") {
        from(components["java"])
        pom {
            version = "${project.version}-SNAPSHOT"
        }
    }
    publications.create<MavenPublication>("hypers") {
        from(components["java"])
        pom {
            groupId = "com.hypers.weicl"
            version = project.version.toString()
        }
    }
    repositories.maven("https://oss.sonatype.org/content/repositories/snapshots") {
        name = "snapshot"
        credentials {
            username = project.ext["mavenUsername"].toString()
            password = project.ext["mavenPassword"].toString()
        }
    }
    repositories.maven("https://nexus3.hypers.cc/repository/orca/") {
        name = "hypers"
        credentials {
            username = project.ext["hypersMavenUsername"].toString()
            password = project.ext["hypersMavenPassword"].toString()
        }
    }
}