import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("io.ktor.plugin") version "2.1.3"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.martina"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11
application {
    mainClass.set("com.martina.demo.DemoApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.5")
    implementation("commons-io:commons-io:2.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.12")
    implementation("org.springframework.boot:spring-boot-starter-security")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

springBoot {
    mainClass.set("com.martina.demo.DemoApplicationKt")
}

tasks.create("MyFatJar", Jar::class) {
    group = "my tasks" // OR, for example, "build"
    description = "Creates a self-contained fat JAR of the application that can be run."
    manifest.attributes["Main-Class"] = "com.martina.demo.DemoApplicationKt"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    with(tasks.jar.get())
}
