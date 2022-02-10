package com.tiptop.trade

import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

abstract class AppTask: DefaultTask() {
    @get:InputFile
    abstract var resourcesDir:File
    @get:OutputFile
    abstract var archiveFile:File
    @get:InputFile
    abstract var imageFile:File
    @get:OutputFile
    abstract var imageOutFile:File
    @TaskAction
    fun runCommand(){
        println("=========================================")
        println("=========================================")
        println("=========================================")
        println("=========================================")
        println("=========================================")
        val out = SevenZOutputFile(archiveFile)
        compressor(out,resourcesDir,"")
        out.close()
        merge(imageOutFile,imageFile,archiveFile)
    }
     private fun compressor(out: SevenZOutputFile, input:File, base:String) {
        if(input.isDirectory){
            out.putArchiveEntry(out.createArchiveEntry(input, "$base/"))
            val newBase = if (base.isEmpty()) "" else "$base/"
            for (file in input.listFiles()) {
                compressor(out,file,newBase+file.name)
            }
        }else{
            out.putArchiveEntry(out.createArchiveEntry(input, base))
            Files.newInputStream(input.toPath()).use {
                out.write(it.readBytes())
                it.close()
                out.closeArchiveEntry()
            }

        }

    }
     private fun merge(out:File, vararg inputs:File) {

        val fos= FileOutputStream(out)
        inputs.forEach {
            it.inputStream().use {
                it.copyTo(fos)
                it.close()
                fos.flush()
            }
        }
        fos.close()
    }
}