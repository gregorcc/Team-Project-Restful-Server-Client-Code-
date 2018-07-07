package edu.uiowa.cs.tornadoFX.components.user.surveyPage

interface SurveyPageController{
    val surveyPageView: SurveyPageView

    fun showFinishView(shake: Boolean  = false)
}