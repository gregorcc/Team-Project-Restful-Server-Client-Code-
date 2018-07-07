package edu.uiowa

import java.io.File

//TODO: implement a common object once the server has started running or for the first call to avoid multiple calls to read and write to file,

class SurveyQuestions(_surveyType: String="not specified", _listOfQuestions: List<String> = emptyList()){
    val surveyType=_surveyType
    val listOfQuestions=_listOfQuestions
}

fun clearQuestionsFile():String { // deletes all contents in the user file
    val size = listOf<String>()
    File("Questions").writeText(size.joinToString { System.lineSeparator() })
    println("Questions Data File has been cleared")
    return ("Questions Data File has been cleared")
}

// add multiple choice questions survey type too. remove duplicates from both.
fun surveyList(): MutableMap<String,List<String>>{ // returns a list of questions.
    val input= File("Questions").bufferedReader()
    val list = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { list.add(it)} }
    var map= mutableMapOf<String,List<String>>()
    for (i in list){
        val split=i.split(" **** ")
        val splitList=split[1].split(" ## ")
        map[split[0]]=splitList
    }
    return map
}

fun writeToQuestionsFile(map: Map<String,List<String>>){
    val write=File("Questions").bufferedWriter()
    for(i in map){
        write.write(i.key+" **** ")
        for( (j,p) in i.value.withIndex()){
            if(j != i.value.size-1){
                write.write(p+" ## ")
            }
            else{
                write.write(p)
            }
        }
        write.newLine()
    }
    write.close()
}

//TODO addQuestionAsServer changes the structure of sequence in the response file so implement those changes here and if necessary in response methods.
fun addQuestionAsServer(updateQuestions: SurveyQuestions): String{
    val map= surveyList().toMutableMap()
    var flag=-1
    for(i in map){
        if(i.key==updateQuestions.surveyType){
            map[i.key]=map[i.key]?.plus(updateQuestions.listOfQuestions)!!
            flag=27
        }
    }
    if(flag==27){
        val write=File("Questions").bufferedWriter()
        for(i in map){
            write.write(i.key+" **** ")
            for( (j,p) in i.value.withIndex()){
                if(j != i.value.size-1){
                    write.write(p+" ## ")
                }
                else{
                    write.write(p)
                }
            }
            write.newLine()
        }
        write.close()
        return "updated ${updateQuestions.surveyType}"
    }
    else if(map.isNotEmpty()){
        val write=File("Questions").bufferedWriter()
        map[updateQuestions.surveyType]= updateQuestions.listOfQuestions
        for (i in map){
            write.write(i.key+" **** ")
            for( (j,p) in i.value.withIndex()){
                if(j != i.value.size-1){
                    write.write(p+" ## ")
                }
                else{
                    write.write(p)
                }
            }
            write.newLine()
        }
        write.close()
        return "created new survey type: ${updateQuestions.surveyType}"
    }
    else{
        val write=File("Questions").bufferedWriter()
        write.write(updateQuestions.surveyType+" **** ")
        for( (j,p) in updateQuestions.listOfQuestions.withIndex()){
            if(j != updateQuestions.listOfQuestions.size-1){
                write.write(p+" ## ")
            }
            else{
                write.write(p)
            }
        }
        write.newLine()
        write.close()
        return "added the very first question to the Database"
    }
}

fun getSurveyAsBackend(map:Map<String,List<String>>, MCmap: MutableMap<String, Map<String,List<String>>>, survey: String):List<Any>{
    var list= listOf<Any>()
    for(i in map){ // for free response questions
        if(i.key==survey){
            list+=i.value
        }
    }
    for(i in MCmap){ // for multiple choice questions
        if(i.key==survey){
            for ( j in i.value){
                val pair= Pair<String, List<String>>(j.key, j.value)
                list+=pair
            }
        }
    }
    return list
}

fun addQuestion(adminList: Map<String,String>, user: User, map: MutableMap<String,List<String>>, updateQuestions: SurveyQuestions): String {
    for(j in adminList){
        if(j.key==user.userName  && j.value==user.password){
                if (map.containsKey(updateQuestions.surveyType)) {
                    map[updateQuestions.surveyType] = map[updateQuestions.surveyType]?.plus(updateQuestions.listOfQuestions)!!
                    return "updated ${updateQuestions.surveyType}"
                }
            map[updateQuestions.surveyType] = updateQuestions.listOfQuestions
            return "created new survey type: ${updateQuestions.surveyType}"
        }
    }
    return "You are not Authorised to modify the survey"
}

fun commonSurveyTypeList(FR: MutableMap<String,List<String>>,MC: MutableMap<String, Map<String,List<String>>>):List<String>{
    val list= FR.keys.toMutableList()
    for (i in MC.keys){
        if(i !in list){
            list+=i
        }
    }
    return list
}
