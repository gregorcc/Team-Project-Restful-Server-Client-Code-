package edu.uiowa.cs.tornadoFX.components.admin.importSurveyPage

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.admin.welcomePage.AdminWelcomePageView
import edu.uiowa.cs.tornadoFX.components.dataStructure.SurveyChoices
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

/*
*  @@author Layton Gao
*
*   class @ImportSurveyView is the UI for people want to import their Survey Question by XLSX, XLS, CSV, those common
*   files, I have an instruction of how Import works, and there is table view to help people visualize what had been
*   Imported. If survey has a title, and question list is not empty, it will send questions to server.
*
* */

class ImportSurveyView : View("Import") {
    private val importSurveyController:ImportSurveyController by inject()

    private val model = object: ViewModel(){
        var surveyTitle = bind{ SimpleStringProperty() }
        val questionList = bind { SimpleListProperty<SurveyChoices>() }
    }

    override val root = borderpane {
        addClass(Styles.wrapper)
        center = vbox (10) {
            form {
                fieldset {
                    field("Survey Title Name:") {
                        textfield(model.surveyTitle){
                            required()
                            style {
                                textFill = Color.BLACK
                            }
                        }
                    }

                    field("Instruction") {
                        label("Support file type, .CSV  .XLSX  .XLS"){
                            style{
                                fontSize = 18.px
                                fontWeight = FontWeight.BOLD
                                effect = javafx.scene.effect.Reflection()
                            }
                        }
                    }

                    field {
                        label (
                            """
                            Import function will read by row, in the order of:
                            Question1 Choice1 Choices2 Choices3 Choices4 Choice5 Choice6
                            Question2 True false
                            Question3 Choice1 ...
                            Multiple Choice will read up to six Choices,
                            When there is empty field, it will start next row
                            Or
                            Question4
                            With no Choice
                            """
                        ){
                            style{
                                fontSize = 18.px
                                fontWeight = FontWeight.BOLD
                                effect = javafx.scene.effect.Reflection()
                            }
                        }
                    }


                }
            }

            right = vbox(25) {
                button("Select File") {
                    action {
                        importSurveyController.setImportFile(model.questionList.value)
                    }
                }

                button("Commit") {
                    action {
                        model.commit {
                            //send Survey Question.
                        }
                    }
                }

                button("Go Back"){
                    action {
                        replaceWith(AdminWelcomePageView::class)
                    }
                }
                alignment = Pos.CENTER_RIGHT
            }

        }

        bottom = tableview<SurveyChoices> {
            //isEditable = true
            items = model.questionList.value

            makeIndexColumn("No.",1 )
            column("Question", SurveyChoices::surveyQuestion){
                enableTextWrap()
                style {
                    fontSize = 18.px
                    fontWeight = FontWeight.BOLD
                }
            }
            column("Choice List", SurveyChoices::choicesList){
                cellFormat {
                    graphic = cache {
                        listview(it?.observable()){
                            style {
                                prefHeight = 150.px
                                prefWidth = 240.px
                                fontSize = 12.px
                            }
                        }
                    }
                }
            }

            smartResize()
            selectionModel.selectionMode = SelectionMode.SINGLE

            style{
                prefHeight = 300.px
                prefWidth = 600.px
            }
        }
    }
}
