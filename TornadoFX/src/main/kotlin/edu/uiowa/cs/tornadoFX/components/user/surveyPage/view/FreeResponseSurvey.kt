package edu.uiowa.cs.tornadoFX.components.user.surveyPage.view

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.SurveyPageModel
import tornadofx.*

// It will display a textarea, and record very words
class FreeResponseSurvey: View(){
    private val surveyPageModel: SurveyPageModel by inject()
    override val root = vbox {
        addClass(Styles.label)
        println(
                """
TornadoFX: -----------------------------------------------------
| surveyPageModel.question   ${surveyPageModel.question.value}
| surveyPageModel.response   ${surveyPageModel.progress.value}
| freeResponse:  ${surveyPageModel.choicesList.value ?: "null"}
|
             """.trimMargin()
        )

        textarea(surveyPageModel.response){
            selectAll()
            isWrapText
            promptText = "Please Type Your Response Here"
            validator{
                if (textProperty().value.isNullOrBlank()) error("Please Left Your Response, Your Opinion matter") else null
            }
        }
    }
}