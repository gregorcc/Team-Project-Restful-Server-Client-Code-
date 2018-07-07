package edu.uiowa.cs.tornadoFX.components.admin.exportDataPage

import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterController
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.text.Text
import javafx.scene.text.TextBuilder
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

//todo: add export on XLSX, XLS file.

@Suppress("DEPRECATION")
class ExportSurveyResponseController: MasterController(){

    fun getCreatedSurveyList(): ObservableList<String> {
        return networkPortal.frontEndGetSurveyList().observable()
    }

    fun exportSelectedSurveyResponse(surveyName:String, format:String){
        // Create a row of string that separate by space and end by \n.
        // So Data Analysis Software, like SAS, R can import this kind data.

        // Network member has not implemented such kind method yet.
        // val responseList = networkPortal.frontEndGetSurveyResponse(surveyName)
        val responseList = listOf(listOf("obs 1","Response 1",
                "Response 2", "Response 3"), listOf("obs 2", "Response 1", "Response 2"))
        var string =""


        if (format == "TXT"){
            for (i in responseList){
                string += i.joinToString(" ") +"\n"
            }
            createTxt(string)
        }
        else if(format == "CSV"){
            for (i in responseList){
                string += i.joinToString(",") +"\n"
            }
            createCsv(string)
        }

    }

    fun createTxt(string: String) {

        val fileChooser = FileChooser()

        //Set extension filter
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"))
        println(
                """
TornadoFX: ----------------Saved Value-----------------------
| createTxt
|
|
|
             """
        )
        //Show save file dialog

        val file = fileChooser.showSaveDialog(null)
        if (file != null) {
            val foo = TextBuilder.create().text(string).build()
            saveFile(foo, file)
        }
    }

    fun createCsv(string: String) {

        val fileChooser = FileChooser()

        //Set extension filter
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"))
        println(
                """
TornadoFX: ----------------Saved Value-----------------------
| createCSV
|
|
|
             """
        )
        //Show save file dialog

        val file = fileChooser.showSaveDialog(null)
        if (file != null) {
            val foo = TextBuilder.create().text(string).build()
            saveFile(foo, file)
        }
    }

    fun saveFile(text: Text, file: File) = try {
        val fileWriter: FileWriter?
        fileWriter = FileWriter(file)
        fileWriter.write(text.text)
        fileWriter.close()
    } catch (ex: IOException) {
        Logger.getLogger(Text::class.java.name).log(Level.SEVERE, null, ex)
    }

}