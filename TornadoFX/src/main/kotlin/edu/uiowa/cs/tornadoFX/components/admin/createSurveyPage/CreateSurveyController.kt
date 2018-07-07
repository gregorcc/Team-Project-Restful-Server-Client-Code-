package edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage

import edu.uiowa.cs.tornadoFX.components.dataStructure.SurveyChoices
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterController
import javafx.collections.FXCollections

/*
*   @@author Layton Gao
*
*   @CreateSurveyController correspond to the buttons in the @CreateSurveyView.
*
* */

class CreateSurveyController: MasterController(){
    val createSurveyList = FXCollections.observableArrayList<SurveyChoices>()
    val selectedQuestion = CreateSurveyModel()
    private val createSurveyModel: CreateSurveyModel by inject()
    var surveyTitle = ""
    private val createSurveyView: CreateSurveyView by inject()

    init {

    }

    fun addNewQuestion(){
        createSurveyList += SurveyChoices("", mutableListOf())
    }

    fun deleteQuestion(i: Int){
        if (i>=0 && i<createSurveyList.size ) createSurveyList.removeAt(i)
        if (i>=createSurveyList.size) createSurveyList.removeAt(createSurveyList.size-1)
    }

    fun sendQuestionToServer(){
        var sum = 0
        createSurveyList.forEach { if(!it.emptyChoicesList()) sum += 1 }
        if (sum == 0){
            println("TornadoFX: Case of FreeResponse")
            networkPortal.adminAddSurvey(getUserNAME(),getUserPASSWORD(), createSurveyModel.surveyTitle.value,castToListOfString(createSurveyList))
        }else{
            if (sum != createSurveyList.size){
                println("TornadoFX: Case of Multiple Choices")
                networkPortal.adminAddSurveyMC(getUserNAME(),getUserPASSWORD(),createSurveyModel.surveyTitle.value +" (Desktop Only)" ,castToMap(createSurveyList))
            }else{
                println("TornadoFX: Case of Mixed of Multiple Choices and FreeResponse")
                networkPortal.adminAddSurveyMC(getUserNAME(),getUserPASSWORD(),createSurveyModel.surveyTitle.value,castToMap(createSurveyList))
            }
        }
    }


    fun refresh(){
        createSurveyView.refresh()
    }

    fun castToListOfString(temp :List<SurveyChoices>):List<String>{
        for (i in temp){
            println(i.toString())
        }
        return temp.filter { !it.surveyQuestion.isNullOrBlank() }.map { it.surveyQuestion }
    }

    fun castToMap(temp: List<SurveyChoices>):Map<String, List<String>>{
        for (i in temp){
            println(i.toString())
        }
        return temp.filter { !it.surveyQuestion.isNullOrBlank() }.map { it.surveyQuestion to (it.choicesList ?: listOf(" ")) }.toMap()
    }
}