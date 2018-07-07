package edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.view

import javafx.scene.layout.BorderPane
import tornadofx.*

/*
*   @@author Layton Gao
*
*   This was like the control panel of different type of question of correspond view.
* */

class ChoiceQuestionTypeView: View(){
    private val multipleChoiceView: MultipleChoiceView by inject()
    private val freeResponseView: FreeResponseView by inject()
    private val trueOrFalseView: TrueOrFalseView by inject()

    override val root = BorderPane()

    init {
        with(root){
            top = vbox {
                togglegroup {
                    radiobutton("Multiple Choices"){
                        action {
                            runAsync {

                            }ui{
                                center.replaceWith(multipleChoiceView.root)
                            }
                        }
                    }
                    radiobutton("True or False Question"){
                        action {
                            runAsync {

                            }ui{
                                center.replaceWith(trueOrFalseView.root)
                            }
                        }
                    }
                    radiobutton("Free Response"){
                        action {
                            runAsync {

                            }ui{
                                center.replaceWith(freeResponseView.root)
                            }
                        }
                    }
                }
            }
            center = BlankView().root
        }
    }
}

