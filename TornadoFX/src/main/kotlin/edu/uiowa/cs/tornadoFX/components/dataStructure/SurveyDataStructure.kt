package edu.uiowa.cs.tornadoFX.components.dataStructure

import tornadofx.*

/*
*   @@author Layton Gao
*
*   class @SurveyChoices is wildly used in @surveyPageView, @CreateSurveyView,
*   It contains a surveyQuestion and a choicesList, choicesList can be empty list.
*   All of the data was delegated by property, so later on in the view system it can be bind by different fields and labels
*
*   fun @emptyChoicesList will ensure the choice list is not empty; empty list can avoid null issue.
*
*   class @SurveyTypeList is used to display all the title of the survey; It's basically, a list of String
*   and I don't feel it is very useful, maybe it will not appear in the final version.
* */

class SurveyChoices(surveyQuestion: String, mutableList: MutableList<String>?){
    var surveyQuestion by property(surveyQuestion)
    fun surveyQuestionProperty() = getProperty(SurveyChoices::surveyQuestion)

    var choicesList by property(mutableList?.observable()?:(0..5).map { "" }.toMutableList().observable())
    fun choicesListProperty() = getProperty(SurveyChoices::choicesList)

    fun emptyChoicesList() = (choicesList?.sumBy { if(it.isNullOrBlank())0 else 1 }?:0) == 0

    override fun toString(): String {
        return surveyQuestion?.toString()?:"null" + " ---|--- " + choicesList?.toString()?:"null"
    }
}

class SurveyTypeList(_surveyTypeList: List<String>){
    val surveyTypeList = _surveyTypeList
    //all the name of surveys, we dont want load all survey one times.
}

class SurveyResponse(surveyName: String){
    var surveyName by property(surveyName)
    fun surveyNameProperty() = getProperty(SurveyResponse::surveyName)
    var responseList: MutableList<String> = mutableListOf() //<username, response to question>
    fun responseListProperty() = getProperty(SurveyResponse::responseList)
}


