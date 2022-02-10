import org.gradle.kotlin.dsl.`kotlin-dsl`
plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("com.gradle.plugin-publish") version "0.20.0"

}
repositories {
    maven(url = "https://maven.aliyun.com/repository/public")
    google()
    mavenCentral()
    jcenter()
}
dependencies {

    implementation("com.android.tools.build:gradle:7.1.1")
    implementation("com.squareup:javapoet:1.13.0")
    api("org.apache.commons:commons-compress:1.21")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation(gradleApi())
    implementation(localGroovy())
}
gradlePlugin{
    plugins {
        create("app"){
            id="com.tiptop.trade"
            displayName="Trade Plugin"
            implementationClass="com.tiptop.trade.AppPlugin"
        }
    }
}
pluginBundle{
    website="https://github.com/henjue/trade_plugin"
    vcsUrl="https://github.com/henjue/trade_plugin"
    description="Trade Plugin"
    tags= listOf("","")

}
group = "com.tiptop.trade"
version="1.0.1"