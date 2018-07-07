package edu.uiowa.cs.tornadoFX.components.user.surveyPage.view

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.SurveyPageModel
import tornadofx.*

// This will create new radio button dependent on how many choices there are.
class MultipleChoiceSurvey: View(){
    private val surveyPageModel: SurveyPageModel by inject()
    override val root = vbox {
        addClass(Styles.label)
        println(
                """
TornadoFX: ----------------Saved Value-----------------------
| surveyPageModel.question   ${surveyPageModel.question.value}
| surveyPageModel.response   ${surveyPageModel.progress.value}
| multipleChoices: ${surveyPageModel.choicesList.value?: "null"}
|
             """
        )

        togglegroup {
            for (i in 0..(surveyPageModel.choicesList.value.size - 1)) {
                if (surveyPageModel.choicesList.value[i].isNotBlank()) {
                    radiobutton(surveyPageModel.choicesList.value[i]) {
                        val temp = i + 1
                        if (surveyPageModel.response.value == temp.toString()) {
                            isSelected = true
                        }
                        action {
                            surveyPageModel.response.value = temp.toString()
                        }
                    }
                }
            }

        }
    }
}