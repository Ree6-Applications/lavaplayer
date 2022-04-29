plugins {
  `java-library`
  groovy
  `maven-publish`
}

val moduleName = "lavaplayer"
version = "1.3.80"

dependencies {
  api("com.github.Ree6-Applications.lavaplayer:lava-common:1.3.80")
  implementation("com.sedmelluq:lavaplayer-natives:1.3.14")
  implementation("org.slf4j:slf4j-api:1.7.36")

  api("org.apache.httpcomponents:httpclient:4.5.13")
  implementation("commons-io:commons-io:2.11.0")

  api("com.fasterxml.jackson.core:jackson-core:2.13.2")
  api("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")

  implementation("org.jsoup:jsoup:1.14.3")
  implementation("net.iharder:base64:2.3.9")

  testImplementation("org.codehaus.groovy:groovy:3.0.10")
  testImplementation("org.spockframework:spock-core:2.1-groovy-3.0")
  testImplementation("ch.qos.logback:logback-classic:1.2.11")
  testImplementation("com.github.Ree6-Applications.lavaplayer:lavaplayer-test-samples:1.3.80")
}

tasks.jar {
  exclude("natives")
}

val updateVersion by tasks.registering {
  File("$projectDir/src/main/resources/com/sedmelluq/discord/lavaplayer/tools/version.txt").let {
    it.parentFile.mkdirs()
    it.writeText(version.toString())
  }
}

tasks.classes.configure {
  finalizedBy(updateVersion)
}

val sourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets["main"].allSource)
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifactId = moduleName
      artifact(sourcesJar)
    }
  }
}
