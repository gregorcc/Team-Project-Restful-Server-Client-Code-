package edu.uiowa.cs.tornadoFX.components.user.welcomePage

import edu.uiowa.cs.tornadoFX.components.user.loginPage.LoginController
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterController
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

/*
*   @@author Layton Gao
*
*   class @WelcomePageController:
*       This page will provide control to a list of user activity. such like log out, forget remember password.
*
*   fun @getSurveyTypeList:
*       This will ask Network Controller to get a list of available survey.
*
*   fun @logout:
*       This will send text to server to indicate user logged out.
* */


// This page will provide control to a list of user activity. such like log out, forget remember password.
open class WelcomePageController: MasterController() {
    private val loginController: LoginController by inject()

    // This will ask Network Controller to get a list of available survey.
    fun getSurveyTypeList() :ObservableList<String> {
//        return FXCollections.observableArrayList("sports",
//                    "Social Survey", "Age Survey", "Financial Survey", "Opinion Survey", "Health Survey")
        println(networkPortal.frontEndGetSurveyList().observable())
        return networkPortal.frontEndGetSurveyList().observable()
    }

    // This will send text to server to indicate user logged out.
    fun logout() {
        println(
                """
TornadoFX: ----------------Saved Value-----------------------
| Start Log out
|
|
|
             """
        )
        networkPortal.frontEndLogOutUser(getUserNAME())
        loginController.showLoginView("Log Out Successful")
    }
}
