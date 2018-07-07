package edu.uiowa.cs.tornadoFX.components.anonymous

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.Popup
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterView
import edu.uiowa.cs.tornadoFX.components.masterComponent.Shakeable
import edu.uiowa.cs.tornadoFX.components.user.surveyPage.SurveyPageModel
import edu.uiowa.cs.tornadoFX.components.user.welcomePage.WelcomePageController
import edu.uiowa.cs.tornadoFX.components.user.welcomePage.WelcomePageView
import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.system.exitProcess

// This is the child of UserWelcomePageView, It contains take survey function only.
class AnonymousWelcomePageView: MasterView("Welcome! Anonymous"), WelcomePageView, Shakeable {
    private val surveyPageModel: SurveyPageModel by inject()
    private val surveyPageController: AnonymousUserSurveyPageController by inject()
    val welcomePageController: WelcomePageController by inject()

    private val model = object: ViewModel(){
        var questionList = welcomePageController.getSurveyTypeList()
    }

    override fun profileButton() = button {
        hide()
    }

    override val checkBox: CheckBox
        get() = checkbox("Your Will Not Be Able Login Back Again As Anonymous"){
            isDisable = true
        }
    // This is the placebo button to tell people your secrete is safe.
    override fun logoutButton()= button("Wipe Out All Privacy And Exit The Survey") {
        style {
            isWrapText = true
            alignment = Pos.CENTER
            backgroundColor += Color.DARKRED
            textFill = Color.WHITE
        }
        action {
            exitProcess(0)
        }
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
                surveyPageController.initialization()
                replaceWith(AnonymousUserSurveyPageView::class)
            }else{
                shakeStage()
                Popup.message ="Please Select Survey"
                find(Popup::class).openModal()
            }
        }
    }

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