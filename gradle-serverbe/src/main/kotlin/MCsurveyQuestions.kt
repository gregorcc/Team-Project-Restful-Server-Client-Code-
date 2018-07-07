package edu.uiowa

import java.io.File

class MCsurveyQuestions(_surveyType: String ,_mapOfQuestions: Map<String, List<String>> ) {
    val surveyType=_surveyType
    val mapOfQuestions=_mapOfQuestions
}

fun surveyMCList(): MutableMap<String, Map<String,List<String>>>{ // returns a list of questions.
    // search for this type of survey in the file and return it... TODO
    val input= File("MCquestions").bufferedReader()
    val list = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { list.add(it)} }
    var map= mutableMapOf<String, Map<String, List<String>>>()
    for (i in list){
        val split=i.split(" **** ")
        val splitList4=split[1].split(" ;;;; ")
        val splitList= splitList4.subList(0,splitList4.size-1)
        val map2 = mutableMapOf<String, List<String>>()
        for ( j in splitList){
            val splitList2 =j.split(" @@ ")
            val splitList3 =splitList2[1].split(" ## ")
            map2[splitList2[0]]=splitList3
        }
        map[split[0]]= map2
    }
    return map
}

fun writeToMCQuestionsFile(map: Map<String, Map<String,List<String>>>){
    val write=File("MCquestions").bufferedWriter()
    for(i in map){
        write.write(i.key+" **** ")
        for( j in i.value){
            write.write(j.key + " @@ ")
            for((k,q) in j.value.withIndex()) {
                if (k != j.value.size - 1) {
                    write.write(q + " ## ")
                } else {
                    write.write(q+ " ;;;; ")
                }
            }
        }
        write.newLine()
    }
    write.close()
}


fun clearMCQuestionsFile():String { // deletes all contents in the user file
    val size = listOf<String>()
    File("MCquestions").writeText(size.joinToString { System.lineSeparator() })
    println("Multiple choice Questions Data File has been cleared")
    return ("Questions Data File has been cleared")
}

fun getMCsurveyAsBackend(map: Map<String, Map<String,List<String>>> ,whichSurvey: String): Map<String, List<String>>{
    if(map.containsKey(whichSurvey)){
        return map[whichSurvey]!!
    }
    else{
        return mapOf()
    }
}

fun addMCquestion(adminList: Map<String,String>, user: User, map: MutableMap<String, Map<String,List<String>>>, updateQuestions: MCsurveyQuestions): String{
    for(j in adminList) {
        if (j.key == user.userName && j.value == user.password) {
            if(map.containsKey(updateQuestions.surveyType)){
                map[updateQuestions.surveyType]= map[updateQuestions.surveyType]!!.plus(updateQuestions.mapOfQuestions)
                return "updated ${updateQuestions.surveyType}"
            }
            else{
                map[updateQuestions.surveyType] = updateQuestions.mapOfQuestions
                return "created new survey type: ${updateQuestions.surveyType} for multiple choice"
            }
        }
    }
    return "You are not Authorised to modify the survey"
}
