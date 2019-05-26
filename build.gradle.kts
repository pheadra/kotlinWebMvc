import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.2.31"
	kotlin("plugin.jpa") version kotlinVersion
	id("org.springframework.boot") version "2.1.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	id("com.adarshr.test-logger") version "1.6.0"
	id("de.jansauer.printcoverage") version "2.0.0"
}

// kotlin-spring, kotlin-jpa를 안해도 되나???
apply {
	from("config/test.gradle")
}

group = "io.haru"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	// kotlin
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))

	// spring
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus")

	// jwt
	implementation("io.jsonwebtoken:jjwt:0.9.0")

	// logging
	implementation("io.github.microutils:kotlin-logging:1.5.9")

	// jackson
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	runtimeOnly("com.h2database:h2")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
	}
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.junit.jupiter:junit-jupiter-params")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	failFast = true
}
