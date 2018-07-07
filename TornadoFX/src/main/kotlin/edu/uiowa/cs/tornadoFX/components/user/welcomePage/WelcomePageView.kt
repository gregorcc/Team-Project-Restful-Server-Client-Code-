package edu.uiowa.cs.tornadoFX.components.user.welcomePage

import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox

/*
*   @@author Layton Gao
*
*   class @UserWelcomePageView:
*       This is the central page allowed user to change profile, logout, take survey. etc.
*
*   fun @profileButton:
*       Change profile allow user to change profile; I set this as a open function, because In Anonymous Welcome page
*       this function will not be available.
*
*   fun @logoutButton:
*       Allow user to logout; I set this as a open function, because In Anonymous Welcome page this function will not
*       be available.
*
*   class @AnonymousWelcomePageView:
*       This is the child of UserWelcomePageView, It contains take survey function only.
* */
interface WelcomePageView{
    fun profileButton(): Button

    fun logoutButton(): Button

    val checkBox: CheckBox

    val comboBox: ComboBox<String>

    fun takeSurveyButton(): Button
}