plugins {
    `java-library`
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

group = "site.ymango"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.querydsl:querydsl-jpa")
    annotationProcessor("com.querydsl:querydsl-apt")

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.hibernate:hibernate-spatial:6.1.7.Final")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val querydslDir = "$buildDir/generated/querydsl"

querydsl {
    jpa = true
    library = "com.querydsl:querydsl-apt"
    querydslSourcesDir = querydslDir
}
sourceSets.getByName("main") {
    java.srcDir(querydslDir)
}
configurations {
    named("querydsl") {
        extendsFrom(configurations.compileClasspath.get())
    }
}