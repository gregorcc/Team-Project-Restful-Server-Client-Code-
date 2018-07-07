package edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.view

import edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.CreateSurveyModel
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/*
*   @@author Layton Gao
*
*   class @MultipleChoiceView:  This is a compromised plan for a MVVM design, I don't know how to bind a element in the
*   list. Maybe, bind element in the list is an awful design.
*   Anywhere, what I did here is just report data in this private model to General model in controller.
*   But good thing in there is, the validator had some kind issue not working when they were exist in multiple view.
*   Now I have a independent private sub-model, validator is working again.
*
* */

class MultipleChoiceView: FreeResponseView(){
    //private val createSurveyModel: CreateSurveyModel by inject()
    private val model =  object: ViewModel(){
        val o1 = bind { SimpleStringProperty() }
        val o2 = bind { SimpleStringProperty() }
        val o3 = bind { SimpleStringProperty() }
        val o4 = bind { SimpleStringProperty() }
        val o5 = bind { SimpleStringProperty() }
        val o6 = bind { SimpleStringProperty() }
        val oo = listOf(o1,o2,o3,o4,o5,o6)
    }
    private val choiceList = model.oo
    //private val choiceList = createSurveyController.selectedQuestion.surveyChoices.value
    //private val choiceList = createSurveyModel.surveyChoices.value
    init {
        with(root){

            center = vbox {
                form {
                    fieldset {
                        field("Choice 1: ") {
                            textfield(choiceList[0]).required()
                        }
                        field("Choice 2: ") {
                            textfield(choiceList[1]).required()
                        }
                        field("Choice 3: ") {
                            textfield(choiceList[2]) {
                                promptText = "This is Optional"
                            }
                        }
                        field("Choice 4: ") {
                            textfield(choiceList[3]) {
                                promptText = "This is Optional"
                                validator {
                                    if (choiceList[2].value.isNullOrBlank() && !choiceList[3].value.isNullOrBlank())
                                        error("Please Fill Choice 3 First") else null
                                }
                            }
                        }
                        field("Choice 5: ") {
                            textfield(choiceList[4]) {
                                promptText = "This is also Optional"
                                validator {
                                    if (choiceList[3].value.isNullOrBlank() && !choiceList[4].value.isNullOrBlank())
                                        error("Please Fill Choice 4 First") else null
                                }
                            }
                        }
                        field("Choice 6: ") {
                            textfield(choiceList[5]) {
                                promptText = "This is still Optional"
                                validator {
                                    if (choiceList[4].value.isNullOrBlank() && !choiceList[5].value.isNullOrBlank())
                                        error("Please Fill Choice 5 First") else null
                                }
                            }
                        }
                    }
                }
                bottom = button("Commit") {
                    shortcut("Enter")
                    setOnAction {
                        println(
                                """
TornadoFX: ----------------Saved Value-----------------------
| Created Survey is already Upload to server
| ${createSurveyController.createSurveyList}
|${createSurveyModel.surveyTitle}
|
             """
                        )
                        //save Question & save Choices
                        model.commit{
                            createSurveyController.selectedQuestion.surveyChoices.value = choiceList.map { it.value?:"" }.observable()
                            createSurveyController.selectedQuestion.commit{
                                createSurveyController.refresh()
                                choiceList.forEach { it.value = "" }
                            }
                        }
                    }
                }
            }
        }
    }
}