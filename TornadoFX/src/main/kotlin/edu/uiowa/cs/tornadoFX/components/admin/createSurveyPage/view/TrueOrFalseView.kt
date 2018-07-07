package edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.view

import tornadofx.*

/*
*   @@author Layton Gao
*
*   This View is similar to @MultipleChoicesView
*   The only different is it write a "True or False" to General Model.
* */


class TrueOrFalseView: FreeResponseView(){
    init {
        with(root){
            center = vbox {
                form {
                    fieldset {
                        field("Chocie 1:"){
                            textfield ("True"){ isDisable = true }
                        }
                        field("Choice 2:"){
                            textfield ("False"){ isDisable = true }
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
                    createSurveyController.selectedQuestion.surveyChoices.value = mutableListOf("True", "False").observable()
                    createSurveyController.selectedQuestion.commit()
                    createSurveyController.refresh()
                }
            }
        }
    }

}