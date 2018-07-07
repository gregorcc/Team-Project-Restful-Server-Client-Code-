package edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.view

import edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.CreateSurveyController
import edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.CreateSurveyModel
import javafx.scene.text.FontWeight
import tornadofx.*

/*
*   @@author Layton Gao
*
*   class @FreeResponseView is the parent class for @TrueOrFalseView, @MultipleChoiceView, and @BlankView
*   it can provide Question field, SurveyTitle for other View. So it will be benefit on model check.
*
*   Of course, it is not the best implement, I should use composite instead inherent. The problem with my code is that,
*   it is too concise. Later on, when people want to change something, it will be hard to operate.
*   But I have not found a better way to refactor this code yet. So, just keep it for now.
*
* */


open class FreeResponseView: View(){
    val createSurveyController: CreateSurveyController by inject()
    val createSurveyModel: CreateSurveyModel by inject()

    fun surveyTitle() = field("Survey Title:"){
        textfield(createSurveyModel.surveyTitle) {
            selectAll()
            style {
                fontSize = 20.px
                fontWeight = FontWeight.EXTRA_BOLD
            }
            //textProperty.addListener{ it -> createSurveyModel.surveyTitle.value = it.toString()}
            validator {
                if (it.isNullOrBlank()) error("Please Inter Your Title") else null
            }
        }
    }

    override val root = borderpane{
        top = hbox(10) {
            form {
                fieldset {
                    add(surveyTitle())

                    field("Question: ") {
                        textfield(createSurveyController.selectedQuestion.surveyQuestion){
                            selectAll()
                            //textProperty.addListener{ _ -> createSurveyController.saveQuestion(createSurveyQuestionModel.question.value)}
                            validator {
                                if (it.isNullOrBlank()) error("Please Inter Your Question") else null
                            }
                        }.required()
                    }
                }
            }
        }

        bottom = button("Commit") {
            shortcut("Enter")
            setOnAction {
                createSurveyController.selectedQuestion.surveyChoices.value = null
                createSurveyController.selectedQuestion.commit{
                    //createSurveyController.surveyTitle = createSurveyModel.surveyTitle.value
                    println(
                            """
TornadoFX: ----------------Saved Value-----------------------
| Created Survey is already Upload to server
| ${createSurveyController.createSurveyList}
|${createSurveyModel.surveyTitle}
|
             """
                    )
                }
                createSurveyController.refresh()
            }
        }
    }
}

