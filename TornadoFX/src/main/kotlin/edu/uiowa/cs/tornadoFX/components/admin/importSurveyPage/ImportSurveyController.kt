package edu.uiowa.cs.tornadoFX.components.admin.importSurveyPage


import edu.uiowa.cs.tornadoFX.components.dataStructure.SurveyChoices
import javafx.stage.FileChooser
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import tornadofx.*
import java.io.BufferedReader
import java.io.FileReader

/*
*   @@author Layton Gao
*
*   class @ImportSurveyController help open FileChooser, and read different type of files.
*
*
*
* */


@Suppress("DEPRECATION")
class ImportSurveyController: Controller(){

    fun setImportFile(questionList: MutableList<SurveyChoices>){
        val fileChooser = FileChooser()
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("All Images", "*.csv", "*.xlsx", "*.xls")
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("CSV", "*.csv")
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("XLS", "*.xls")
        val file = fileChooser.showOpenDialog(null)
        if (file!=null){
            val tempList = if (file.extension == "csv"|| file.extension == "CSV") readCSV(file)
            else readXLSX(file)
            if (tempList != null) questionList.addAll(tempList)
        }
    }

    fun readCSV(csvFile: File): MutableList<SurveyChoices>?{

        val surveyList = mutableListOf<SurveyChoices>()
        var line: String?
        val cvsSplitBy = ","
        println(csvFile.path)
        try {
            BufferedReader(FileReader(csvFile)).use({ br ->
                line = br.readLine()
                while (line != null) {
                    println(line)
                    // use comma as separator
                    val survey = line!!.split(cvsSplitBy.toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
                    if (!survey[0].isBlank()) {
                        surveyList += if (survey.size == 1) (SurveyChoices(survey[0],null))
                        else (SurveyChoices(survey[0],survey.subList(1,survey.size-1)))
                    }
                    line = br.readLine()
                }

            })
            return surveyList
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun readXLSX(file: File):MutableList<SurveyChoices>?{
        try {
            val inputStream = FileInputStream(file)
            println(file.path)
            val workbook = getRelevantWorkbook(inputStream, file.path)
            val surveyList = mutableListOf<SurveyChoices>()
            val firstSheet = workbook.getSheetAt(0)
            val iterator = firstSheet.iterator()

            while (iterator.hasNext()) {
                val nextRow = iterator.next()
                val cellIterator = nextRow.cellIterator()
                val survey = mutableListOf<String>()
                while (cellIterator.hasNext()) {
                    val cell = cellIterator.next()
                    when (cell.cellType) {
                        Cell.CELL_TYPE_STRING -> survey += (cell.stringCellValue.toString().trim())
                        Cell.CELL_TYPE_NUMERIC -> survey += (cell.numericCellValue.toString())
                        Cell.CELL_TYPE_BOOLEAN -> survey += (cell.booleanCellValue.toString())
                        else -> {
                            println("???")
                        }
                    }
                }
                if (!survey.isEmpty()){
                    surveyList += if (survey.size == 1) (SurveyChoices(survey[0],null))
                    else (SurveyChoices(survey[0],survey.subList(1,survey.size-1)))
                }

            }

            workbook.close()
            inputStream.close()
            return surveyList
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    @Throws(IOException::class)
    private fun getRelevantWorkbook(inputStream: FileInputStream, excelFilePath: String): Workbook {

        return when {
            excelFilePath.endsWith("xls") -> HSSFWorkbook(inputStream)
            excelFilePath.endsWith("xlsx") -> XSSFWorkbook(inputStream)
            else -> throw IllegalArgumentException("Incorrect file format")
        }
    }
}

