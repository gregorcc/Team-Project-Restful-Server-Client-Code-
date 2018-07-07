package edu.uiowa.cs.tornadoFX.components.user.surveyPage

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.masterComponent.Shakeable
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.view.FreeResponseSurvey
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.view.MultipleChoiceSurvey
import edu.uiowa.cs.tornadoFX.components.user.welcomePage.UserWelcomePageView
import tornadofx.*

/*
*   @@author Layton Gao
*
*   class @UserSurveyPageView
*       progress bar will read number in the model and show the progress.
*       This class take user to take survey. It has two helper class @FreeResponseSurvey, @MultipleChoiceSurvey
*       They will show different type of survey, like FreeResponse, MultipleChoice, and True or False will be use
*       Multiple Choice.
*       Inside UI, there is three button take use to next question, previous question, and leave the survey, back to
*       welcome page.
*       If user leaved the survey, then it will no send to Server.
*       next question, previous question will trigger action in Controller, So Controller will save and read response
*       and new question.
*
*   fun @update
*       It will decide which question is the correct type to display.
*
*   class @FreeResponseSurvey (in the view folder)
*       It will display a textarea, and record very words
*
*   class @MultipleChoiceSurvey (in the view folder)
*       This will create new radio button dependent on how many choices there are, and record choice id.
*
* */

open class UserSurveyPageView : View("Survey"), SurveyPageView, Shakeable {
    override val surveyPageController: UserSurveyPageController by inject()
    private val surveyPageModel: SurveyPageModel by inject()

    fun update(){
        runLater {
            // It will decide which question is the correct type to display.
            if (surveyPageModel.choicesBoolean.value) root.center.replaceWith(MultipleChoiceSurvey().root)
            else root.center.replaceWith(FreeResponseSurvey().root)
        }
    }
    open fun leaveSurvey() = button("Leave Survey") {
        style{ maxWidth = 150.px }
        action{
            surveyPageController.cleanResponse()
            replaceWith(UserWelcomePageView::class, sizeToScene = true, centerOnScreen = true)
        }
    }

    override val root = borderpane {
        addClass(Styles.wrapper)

        //initialization will load the first question.
        //userSurveyPageController.initialization()
        top = vbox{
            // show the progress of survey.
            progressbar(surveyPageModel.progress){ style { prefWidth = 256.px }}
            label("Question:")
            label(surveyPageModel.question)
        }


        // Here will insert @FreeResponseSurvey or @MultipleChoiceSurvey dependent on question type.
        center = label("No such Survey Exist")

        bottom = hbox (25) {
            addClass(Styles.row)

            //next question, previous question will trigger action in Controller, So Controller will save and read response
            //and new question.
            button("Previous Question") {
                shortcut("Right")
                style{ maxWidth = 150.px }
                action {
                    // Check for the validator is satisfied.
                    surveyPageController.prevQuestion()
                }
            }
            // If user leaved the survey, then it will no send to Server.
            add(leaveSurvey())

            button("Next Question") {
                style{ maxWidth = 150.px }
                shortcut("Left")
                shortcut("Enter")
                action{
                    if (surveyPageController.checkResponse()){
                        surveyPageController.nextQuestion()
                    }
                    // This will indicate whether it's last question or not.
                    // It also bring user to finish page when all question has been finished.
                    text = if (surveyPageController.lastQuestion()) "Last Question"
                    else "Next Question"
                }
            }
        }
    }

}


