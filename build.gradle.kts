
plugins {
    id("java")
}

group = "org.pokemon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.30")
    implementation("org.projectlombok:lombok:1.18.26")
    implementation("org.projectlombok:lombok:1.18.26")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("org.mybatis:mybatis:3.5.13")

}
tasks.test {
    useJUnitPlatform()
}
