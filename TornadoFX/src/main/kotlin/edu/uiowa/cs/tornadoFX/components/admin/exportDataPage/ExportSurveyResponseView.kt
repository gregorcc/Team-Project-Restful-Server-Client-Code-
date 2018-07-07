package edu.uiowa.cs.tornadoFX.components.admin.exportDataPage

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.Popup
import edu.uiowa.cs.tornadoFX.components.admin.welcomePage.AdminWelcomePageView
import edu.uiowa.cs.tornadoFX.components.masterComponent.Shakeable
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.paint.Color
import tornadofx.*

/*
*   @@author Layton Gao
*
*   I plan to show admin how was the progress in the survey.
*   But it turns out to be very time consuming for my group mate to complete a task like this.
* */

class ExportSurveyResponseView: View(),Shakeable {
    val exportSurveyResponseController: ExportSurveyResponseController by inject()
    override val root = borderpane()

    private val model = object: ViewModel(){
        val completion = bind{ SimpleIntegerProperty()}
        val createdQuestionList = exportSurveyResponseController.getCreatedSurveyList()
    }

    private val comboBox = combobox<String> {
        items = model.createdQuestionList
        promptText = "Please Select a Survey"
        style { backgroundColor += Color.WHITESMOKE }
        //addClass(Styles.noStyles)
    }
    init {
        with(root){
            addClass(Styles.wrapper)
            top = vbox {
                progressindicator(model.completion)
            }

            left = vbox(20) {
                add(comboBox)

                button("Export Survey Response as CSV"){
                    action {
                        println(comboBox.selectedItem)
                        if (comboBox.selectedItem != null){
                            exportSurveyResponseController.exportSelectedSurveyResponse(comboBox.selectedItem ?:"","CSV")
                        }else{
                            shakeStage()
                            Popup.message ="Please Select Survey"
                            find(Popup::class).openModal()
                        }
                    }
                }

                button("Export Survey Response as TXT"){
                    action {
                        println(comboBox.selectedItem)
                        if (comboBox.selectedItem != null){
                            exportSurveyResponseController.exportSelectedSurveyResponse(comboBox.selectedItem ?:"","TXT")
                        }else{
                            shakeStage()
                            Popup.message ="Please Select Survey"
                            find(Popup::class).openModal()
                        }
                    }
                }
            }


            bottom = button("Go back") {
                action {
                    replaceWith(AdminWelcomePageView::class)
                }
            }
        }
    }
}