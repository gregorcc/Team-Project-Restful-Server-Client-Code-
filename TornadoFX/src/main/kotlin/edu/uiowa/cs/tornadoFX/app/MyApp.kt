package edu.uiowa.cs.tornadoFX.app

import edu.uiowa.cs.tornadoFX.components.user.loginPage.LoginController
import edu.uiowa.cs.tornadoFX.components.user.loginPage.UserLoginView
import edu.uiowa.cs.tornadoFX.components.user.welcomePage.WelcomePageController
import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*
import java.net.URI

/*
*   @@author Layton Gao
*
*   I don't feel I have done enough for this project.
*   After I try refactor many times, it's still a petty elementary, simple, stupid app.
*   I knew nothing.
*
* */

class MyApp: App(UserLoginView::class, Styles::class){
    private val loginController: LoginController by inject()
    private val welcomePageController: WelcomePageController by inject()

    init {
        //Catch Exceptions
        Thread.UncaughtExceptionHandler { t, e ->
            if (Platform.isFxApplicationThread()) {
                alert(Alert.AlertType.WARNING, "We encounter Some Serious Problem...", e.toString())
            } else {
                alert(Alert.AlertType.WARNING, "We encounter Some Serious Problem...", "An unexpected error occurred in $t")
            }
        }
    }

    //init login controller if there is username and password in the config, it will auto login.
    override fun shouldShowPrimaryStage(): Boolean {
        addStageIcon(Image(URI("file:src/main/resources/icon.jpg").toString()))
        return super.shouldShowPrimaryStage()
    }

    override fun start(stage: Stage) {
        super.start(stage)
        loginController.init()
    }

    //when program exit, it will auto logout.
    override fun stop() {
        welcomePageController.logout()
        super.stop()
    }
}


//interface FrontEndNetworking {
//    fun LoginTransmit(username:String, password: String): Boolean
//    fun createNewUser(newUser: String): Boolean
//    fun GetSurvey(surveyType: String): String
//    fun SubmitAnswers(username: String, answers: List<String>, surveyType: String): Boolean
//}