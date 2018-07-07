package edu.uiowa.cs.tornadoFX.components.anonymous

import edu.uiowa.cs.tornadoFX.components.user.surveyPage.UserSurveyPageView
import javafx.scene.layout.BorderPane
import tornadofx.*

class AnonymousUserSurveyPageView: UserSurveyPageView(){
    override val surveyPageController: AnonymousUserSurveyPageController by inject()
    override val root: BorderPane
        get() = super.root

    override fun leaveSurvey() = button("Return to WelcomePage") {
        style{ maxWidth = 150.px }
        action{
            replaceWith(AnonymousWelcomePageView::class, sizeToScene = true, centerOnScreen = true)
        }
    }
}