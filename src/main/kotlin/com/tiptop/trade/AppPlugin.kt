package com.tiptop.trade

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.buildscript
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.io.File


private typealias AndroidExtension = BaseExtension

abstract class AppPluginExtension {
    abstract val fileName: Property<String>
    abstract val suffix: Property<String>

    init {
        fileName.convention("resources")
        suffix.convention("7z")
    }
}


class AppPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.configureAndroid()
        project.configureApp()
    }

}


internal fun Project.configureAndroid() = this.extensions.getByType<AndroidExtension>().run {

}
internal fun Project.configureApp() = this.extensions.getByType<AppExtension>().run {
        val config = project.extensions.create<AppPluginExtension>("AppPlugin")
        applicationVariants.all {

            project.tasks.create<AppTask>("createZipAssets${name.capitalize()}"){
                group = "build"
                val assetsDir = mergeAssetsProvider.get().outputDir.get().asFile
                resourcesDir= projectDir.resolve("resources")
                archiveFile = File(assetsDir, "${config.fileName.get()}.${config.suffix.get()}")
                imageFile = File(resourcesDir, "cover.jpg")
                imageOutFile=File(assetsDir,imageFile.name).absoluteFile
            }.run {
                mergeAssetsProvider.get().finalizedBy(this)
            }
        }
}