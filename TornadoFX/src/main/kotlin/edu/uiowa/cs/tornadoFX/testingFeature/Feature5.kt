//@file:Suppress("DEPRECATION")
//
//package edu.uiowa.cs.tornadoFX.testingFeature
//
//import edu.uiowa.cs.tornadoFX.components.dataStructure.SurveyChoices
//import java.io.File
//import java.io.FileInputStream
//import java.io.IOException
//import org.apache.poi.hssf.usermodel.HSSFWorkbook
//import org.apache.poi.ss.usermodel.Cell
//import org.apache.poi.ss.usermodel.Workbook
//import org.apache.poi.xssf.usermodel.XSSFWorkbook
//
//object ExcelReader {
//
//    @JvmStatic
//    fun readXLSX(file: File) = try {
//        val excelFilePath = "C:\\Users\\42times28\\Desktop\\test.xlsx"
//        val inputStream = FileInputStream(file)
//        println(excelFilePath)
//        val workbook = getRelevantWorkbook(inputStream, excelFilePath)
//        val surveyList = mutableListOf<SurveyChoices>()
//        val firstSheet = workbook.getSheetAt(0)
//        val iterator = firstSheet.iterator()
//
//        while (iterator.hasNext()) {
//            val nextRow = iterator.next()
//            val cellIterator = nextRow.cellIterator()
//            val survey = mutableListOf<String>()
//            while (cellIterator.hasNext()) {
//                val cell = cellIterator.next()
//                when (cell.cellType) {
//                    Cell.CELL_TYPE_STRING -> survey += (cell.stringCellValue.toString().trim())
//                    Cell.CELL_TYPE_NUMERIC -> survey += (cell.numericCellValue.toString())
//                    Cell.CELL_TYPE_BOOLEAN -> survey += (cell.booleanCellValue.toString())
//                    else -> {
//                        println("???")
//                    }
//                }
//            }
//            if (!survey.isEmpty()){
//                if (survey.size == 1) surveyList += (SurveyChoices(survey[0],null))
//                    else surveyList += (SurveyChoices(survey[0],survey.subList(1,survey.size-1)))
//            }
//
//        }
//        for (i in surveyList){
//            println(i.toString())
//        }
//
//        workbook.close()
//        inputStream.close()
//
//    } catch (e: IOException) {
//        // TODO Auto-generated catch block
//        e.printStackTrace()
//    }
//
//    @Throws(IOException::class)
//    private fun getRelevantWorkbook(inputStream: FileInputStream, excelFilePath: String): Workbook {
//        var workbook: Workbook? = null
//
//        if (excelFilePath.endsWith("xls")) {
//            workbook = HSSFWorkbook(inputStream)
//        } else if (excelFilePath.endsWith("xlsx")) {
//            workbook = XSSFWorkbook(inputStream)
//        } else {
//            throw IllegalArgumentException("Incorrect file format")
//        }
//
//        return workbook
//    }
//
//}
//
