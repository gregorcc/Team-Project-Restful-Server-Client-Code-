package edu.uiowa.cs.tornadoFX.components.anonymous

import edu.uiowa.cs.tornadoFX.components.user.surveyPage.SurveyPageController
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.UserSurveyPageController

class AnonymousUserSurveyPageController: UserSurveyPageController(), SurveyPageController {
    override val surveyPageView: AnonymousUserSurveyPageView by inject()

    override fun showFinishView(shake: Boolean) {
        surveyPageView.replaceWith(AnonymousFinishPageView::class, sizeToScene = true, centerOnScreen = true)
    }
}