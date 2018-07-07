package edu.uiowa.cs.tornadoFX.components.user.welcomePage

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.user.loginPage.UserLoginView
import edu.uiowa.cs.tornadoFX.components.user.newUserPage.ChangeUserProfileView
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.UserSurveyPageView
import edu.uiowa.cs.tornadoFX.components.Popup
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterView
import edu.uiowa.cs.tornadoFX.components.masterComponent.Shakeable
import edu.uiowa.cs.tornadoFX.components.user.loginPage.LoginController
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.UserSurveyPageController
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.SurveyPageModel
import javafx.scene.paint.Color
import tornadofx.*


open class UserWelcomePageView : MasterView("Survey") , Shakeable, WelcomePageView {
    val welcomePageController: WelcomePageController by inject()
    private val loginController: LoginController by inject()
    val surveyPageModel: SurveyPageModel by inject()
    val userSurveyPageController: UserSurveyPageController by inject()
    private val model = object: ViewModel(){
        var questionList = welcomePageController.getSurveyTypeList()
    }

    override fun takeSurveyButton() = button("Start Survey") {
        shortcut("Enter")
        action {
            if (comboBox.selectedItem != null){
                println(
                        """
TornadoFX: ----------------Saved Value-----------------------
| Get Available Survey Name
| ${comboBox.selectedItem}
|
|
             """
                )
                surveyPageModel.surveyTitle.value = comboBox.selectedItem
                userSurveyPageController.initialization()
                replaceWith(UserSurveyPageView::class)
            }else{
                shakeStage()
                Popup.message ="Please Select Survey"
                find(Popup::class).openModal()
            }
        }
    }

    override fun profileButton() = button("Change Profile"){
        action {
            replaceWith(ChangeUserProfileView::class)
        }
    }

    override fun logoutButton() = button("Log Out"){
        userSurveyPageController.vanishResponse()
        action {
            if (checkBox.isSelected) loginController.forgetMe()
            welcomePageController.logout()
            replaceWith(UserLoginView())
        }
    }
    override val checkBox = checkbox("Forget Remembered Password")

    override val comboBox = combobox<String> {
        items = model.questionList
        promptText = "Please Select a Survey"
        style { backgroundColor += Color.WHITESMOKE }
    }

    init{
        with(root){
            center = borderpane {
                addClass(Styles.wrapper)

                top = label("Welcome!")
                center = vbox (25) {
                    add(comboBox)
                    add(takeSurveyButton())
                    add(profileButton())
                    add(checkBox)
                    add(logoutButton())
                }
            }
        }
    }
}


