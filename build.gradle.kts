import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("org.openapi.generator") version "6.0.1"
}

group = "com.spring.sharding"
version = "0.0.1-SNAPSHOT"
val basePackage = "com.spring.sharding"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

tasks.compileKotlin { dependsOn( "openApiGenerate") }
openApiGenerate {
	generatorName.set("spring")
	inputSpec.set("$rootDir/src/main/resources/static/api-docs.yaml")
	outputDir.set ("$buildDir/generated/openapi")
	modelNameSuffix.set("Dto")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8",
			"gradleBuildFile" to "false",
			"basePackage" to "$basePackage.application.web.api",
			"apiPackage" to "$basePackage.application.web.api",
			"modelPackage" to "$basePackage.application.web.dto",
			"interfaceOnly" to "true",
			"hideGenerationTimestamp" to "true",
			"openApiNullable" to "false",
			"additionalModelTypeAnnotations" to "@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)"
		)
	)
}
sourceSets { getByName("main") { java { srcDir("$buildDir/generated/openapi/src/main/java") } } }

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude("org.springframework.boot", "spring-boot-starter-tomcat")
	}

	implementation("org.springframework.boot:spring-boot-starter-jetty")
	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("org.openapitools:jackson-databind-nullable:0.2.3")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
	compileOnly("io.swagger:swagger-annotations:1.6.6")
	compileOnly("io.springfox:springfox-core:3.0.0")

	// DB
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

springBoot{ buildInfo() }