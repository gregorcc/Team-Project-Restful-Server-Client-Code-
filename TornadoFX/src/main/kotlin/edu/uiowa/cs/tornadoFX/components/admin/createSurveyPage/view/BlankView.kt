package edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.view

import tornadofx.*

/*
*   @@author Layton Gao
*
*   This is the defeat page when type of Question has no be selected.
* */

open class BlankView: FreeResponseView(){

    override val root = borderpane{

        center = form {
            fieldset {
                add(surveyTitle())
            }
        }
        bottom = button("Commit") {
            action {
                createSurveyController.selectedQuestion.commit{
                }
            }
        }
    }

}