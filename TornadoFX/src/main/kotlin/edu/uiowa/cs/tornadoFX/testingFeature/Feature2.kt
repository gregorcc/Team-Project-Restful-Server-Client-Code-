package edu.uiowa.cs.tornadoFX.testingFeature

import java.io.PrintStream


fun main(args: Array<String>) {
    // Run a java app in a separate system process
    var proc = Runtime.getRuntime().exec("java.exe -jar src/main/lib/Mario.jar")
    // Then retreive the process output
    //var `in` = proc.inputStream
    var err = proc.errorStream
    //var out = proc.outputStream
    while (proc.isAlive){
        println("--------------------")
        println(err.read())
        //println(out)
        var out = proc.outputStream
        val printStream = PrintStream(out)
        printStream.println()
        printStream.flush()
        printStream.close()
        println(out.toString())

        Thread.sleep(3000)
    }


}
