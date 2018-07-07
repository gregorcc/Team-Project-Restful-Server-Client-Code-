package edu.uiowa.cs.tornadoFX.components.user.surveyPage

import edu.uiowa.cs.tornadoFX.components.dataStructure.SurveyChoices
import edu.uiowa.cs.tornadoFX.components.Popup
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterController
import edu.uiowa.cs.tornadoFX.components.user.FinishPage.UserFinishPageView
import javafx.beans.property.Property
import javafx.collections.ObservableList
import tornadofx.*

/*
*   @@author Layton Gao
*
*   When I realized this is insane. It's too later, for me to refactor it...
*   It's bad code, because I put the update question list, response list. check question type, send response, anything
*   in one controller.
*   Indeed, I have not come up any good idea to reduce complexity. I tried to use wizard, but it's not very suitable.
*   I also tried to pull out some response as an independent controller, but it does not do so much.
*   This part, unlike create survey, which can bind everything in the model, is much hard then I originally thought.
*   I can bind Lists and it's sub element at same time. I think that is why it be come so hard.
*   Maybe I need to develop custom view module to solve this problem. However, After some research, I found javafx
*   has not such kind function to duplicate views.
*
*   class @UserSurveyPageController:
*       Read data from serve, and load data to model.
*
*   fun @initialization:
*       Load first question to model.
*
*   fun @prevQuestion:
*       Load previous question and response(if it is exist).
*
*   fun @nextQuestion:
*       Load next question and response(if it is exist).
*
*   fun @freeOrMultiple:
*       Check whether new question is free or Multiple.
*
*   fun @showSurveyView:
*       Shake the stage.
*
*   fun @viewProgress:
*       Show how many question remained.
*
*   fun @lastQuestion:
*       Show tall whether this question is the last one.
*
*   fun @toString:
*       Return all lists inside UserSurveyPageController.
* */

// Read data from serve, and load data to model.
open class UserSurveyPageController: MasterController(), SurveyPageController {
    override val surveyPageView: UserSurveyPageView by inject()
    private val surveyPageModel: SurveyPageModel by inject()
    private var responseMap= mutableMapOf<String, ObservableList<String>>()
    private var responseList= mutableListOf("","","").observable()
    private var questionsList: ObservableList<SurveyChoices> = mutableListOf(SurveyChoices("Q1", mutableListOf("C1", "C2", "C3")),
            SurveyChoices("beta", null), SurveyChoices("Q3", mutableListOf("True", "False"))).observable()
    private var indicatorNumber = 0

    // Load first question to model.
    fun initialization(){
        val temp = networkPortal.frontEndGetSurvey(surveyPageModel.surveyTitle.value)
        for(i in responseMap){
            println(
                    """
TornadoFX: ----------------Saved Value-----------------------
| responseMap key       ${i.key}
| responseMap Value     ${i.value}
|
|
             """
            )
        }
        questionsList = castToSurveyChoices(temp)
        responseList = responseMap[surveyPageModel.surveyTitle.value]?: (0..(questionsList.size-1)).map { "" }.observable()
        indicatorNumber = 0
        surveyPageModel.response.value = responseList[indicatorNumber]
        runLater {
            viewProgress()
            freeOrMultiple(surveyPageModel.question, surveyPageModel.choicesList)
        }
    }

    // Load previous question and response(if it is exist).
    fun prevQuestion(){
        runAsync {

            // save the response when question changed.
            responseList[indicatorNumber] = surveyPageModel.response.value
            indicatorNumber>0
        } ui { prevQuestions ->

            // If there is previous question exist, then load previous question.
            if(prevQuestions) {
                indicatorNumber -= 1
                // update progress.
                viewProgress()

                // load new question.
                surveyPageModel.response.value = responseList[indicatorNumber]
                freeOrMultiple(surveyPageModel.question,surveyPageModel.choicesList)

            // If there is not more question in front, then pop up say no more question.
            }else{
                showSurveyView("This is the first Question!", true)
            }
        }
    }

    // Load next question and response(if it is exist).
    fun nextQuestion(){
        runAsync {

            // save the response when question changed.
            responseList[indicatorNumber] = surveyPageModel.response.value
            indicatorNumber<questionsList.size -1
        } ui { nextQuestions ->

            // If there is previous question exist, then load previous question.
            if(nextQuestions) {
                indicatorNumber += 1
                // update progress.
                viewProgress()

                // load new question.
                surveyPageModel.response.value = responseList[indicatorNumber]
                freeOrMultiple(surveyPageModel.question,surveyPageModel.choicesList)


                println(
                        """
TornadoFX: ----------------Saved Value-----------------------
| questionList   $questionsList
| responseList   $responseList
|
|
             """
                )
            }else{
                responseList[indicatorNumber] = surveyPageModel.response.value
                println(
                        """
TornadoFX: ----------------Saved Value-----------------------
| questionList   $questionsList
| responseList   $responseList
|
|
             """
                )
                indicatorNumber = 0
                submitResponse()
            }
            // If there is not more question in front, then it will bring user to finish page.
        }
    }

    // Check whether new question is free or Multiple.
    private fun freeOrMultiple(question: Property<String>, choicesList: Property<ObservableList<String>>) {
        question.value = questionsList[indicatorNumber].surveyQuestion

        // if there is choice list, then it is Multiple choice question.
        // if there is no choice list, then it is free response question.
        if (questionsList[indicatorNumber].choicesList?.isNotEmpty() == true && !questionsList[indicatorNumber].emptyChoicesList()  ) {
            choicesList.value = questionsList[indicatorNumber].choicesList?.observable()
            surveyPageModel.choicesBoolean.value = true
        } else {
            surveyPageModel.choicesBoolean.value = false
        }
        surveyPageView.update()
    }

    fun cleanResponse(){
        for(i in responseMap){
            println(
                    """
TornadoFX: ----------------Saved Value-----------------------
| responseMap key       ${i.key}
| responseMap Value     ${i.value}
|
|
             """
            )
        }
        responseMap[surveyPageModel.surveyTitle.value] = responseList
        responseList= mutableListOf("").observable()
    }

    fun vanishResponse(){
        responseMap.clear()
    }

    private fun submitResponse(){
        runAsync {
            networkPortal.frontEndSubmitAnswers(getUserNAME(),surveyPageModel.surveyTitle.value,responseList)
        }ui { itworks ->
            if (itworks){
                showFinishView()
                cleanResponse()
            }else{
                Popup.message = "Network Error, Please Check Your Network Setting and Try Again!"
                find(Popup::class).openModal()
                surveyPageView.shakeStage()
            }
        }
    }

    // Shake the stage.
    private fun showSurveyView(message: String?, shake: Boolean = false) {
        if (message != null) {
            Popup.message = message
            find(Popup::class).openModal()
        }
        runLater {
            surveyPageView.replaceWith(surveyPageView, sizeToScene = true, centerOnScreen = true)
            if (shake) surveyPageView.shakeStage()
        }
    }

    override fun showFinishView(shake: Boolean) {
        surveyPageView.replaceWith(UserFinishPageView::class, sizeToScene = true, centerOnScreen = true)
    }

    fun checkResponse():Boolean{
        if (surveyPageModel.response.value.isNullOrBlank()) {
            showSurveyView("Please Leave Your Response",true)
            return false
        }
        return true
    }

    // Show how many question remained.
    private fun viewProgress(){
        println(indicatorNumber.toDouble() / questionsList.size.toDouble())
        surveyPageModel.progress.value = (indicatorNumber).toDouble()/questionsList.size.toDouble()
    }

    // Show tall whether this question is the last one.
    fun lastQuestion() = (questionsList.size - indicatorNumber) == 2

    // Return all lists inside UserSurveyPageController.
    override fun toString():String {
        return questionsList.toList().toString() +"\n"+ responseList.toString()
    }

    private fun castToSurveyChoices(list:List<*>):ObservableList<SurveyChoices>{
        return list.map {
            when (it) {
                is String -> {println("TornadoFX: cast String $it");SurveyChoices(it, null)}
                is Map<*,*> -> {println("TornadoFX: cast Pair $it");SurveyChoices(it["first"] as String ,it["second"] as MutableList<String>)}
                else -> {println("TornadoFX: cast Null $it");SurveyChoices("",null)}
            }
        }.observable()
    }
}

