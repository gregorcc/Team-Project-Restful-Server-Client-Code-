package edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage


import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.view.ChoiceQuestionTypeView
import edu.uiowa.cs.tornadoFX.components.admin.welcomePage.AdminWelcomePageView
import edu.uiowa.cs.tornadoFX.components.dataStructure.SurveyChoices
import javafx.beans.Observable
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

/*
*   @@author Layton Gao
*
*   class @CreateSurveyView allow admin to create a new survey.
*   The design in this CreateSurvey is simple.
*   Basically, Left side, there is a table of question and choice List bind by @CreateSurveyModel
*   Admin need to choose one of type of free response question, true or false question, multiple choices question
*   and add to new question. Different type of question correlate to different subview. Once type was selected
*   The new view will be inject to this View and then Admin can add more details about question and choices.
*
*   This use different strategy or approach compare to User's take survey. In User's surveyView, I want hide details
*   as few as possible, so nothing can affect the correctness of the survey. In Admin's createSurvey, I want add more
*   details, so admin can have a better bigger picture of what was going on.
*
* */

class CreateSurveyView : View("Create New Survey") {
    private val adminWelcomePageView: AdminWelcomePageView by inject()
    private val choiceQuestionTypeView: ChoiceQuestionTypeView by inject()
    private val createSurveyController: CreateSurveyController by inject()
    private val createSurveyModel: CreateSurveyModel by inject()
    private var index = 0

    override val root = borderpane()


    private val tableView = tableview<SurveyChoices> {
        items = createSurveyController.createSurveyList
        bindSelected(createSurveyController.selectedQuestion)
        makeIndexColumn("No.",1 )
        column("Question", SurveyChoices::surveyQuestion){
            cellFormat {
                graphic = cache {
                    vbox {
                        label(it) {
                            style {
                                textFill = Color.BLACK
                                effect = javafx.scene.effect.Reflection()
                                fontWeight = FontWeight.BOLD
                                fontSize = 16.px
                            }
                        }
                    }
                }
            }
        }
        column("Choice List", SurveyChoices::choicesList){
            cellFormat {
                graphic = cache {
                    listview<String>{
                        items = it?.observable()
                        style {
                            prefHeight = 150.px
                            fontSize = 12.px
                        }
                        isEditable = true
                    }
                }
            }
        }

        smartResize()
        selectionModel.selectionMode = SelectionMode.SINGLE

        // Check selected question.
        selectionModel.selectedItems.addListener {
            observable: Observable? ->
            if (observable != null){
                index = selectionModel.selectedIndex
                refresh()
                println(createSurveyController.selectedQuestion.surveyChoices.value?.toString()?:"null")
                root.center.replaceWith(ChoiceQuestionTypeView().root)
            }

            if (selectionModel.selectedIndex == -1){
                createSurveyController.createSurveyList.size -1
            }
        }

        style{
            prefHeight = 520.px
            prefWidth = 300.px
        }
    }

    init {
        with(root){
            addClass(Styles.wrapper)

            left = borderpane {

                left = tableView
                bottom = hbox {
                    button("Add New Question") {
                        action {
                            createSurveyController.addNewQuestion()
                        }
                    }

                    button("Delete Selected Question") {
                        action {
                            createSurveyController.deleteQuestion(index)
                        }
                    }
                }
            }

            // This is the placeholder for the sub-views
            center = choiceQuestionTypeView.root

            right = hbox {
                button("Submit") {
                    action {
                        createSurveyModel.commit{
                            createSurveyController.sendQuestionToServer()
                            replaceWith(adminWelcomePageView::class)
                        }
                    }
                }

                button("Go Back") {
                    action {
                        replaceWith(adminWelcomePageView::class)
                    }
                }
                alignment = Pos.BOTTOM_RIGHT
            }
        }
    }

    // Because inside table view, each observation contains a list view, and list view will auto auto refresh since it's
    // not bind by property, and I can't figure out how to bind elements in list, I need to refresh table view after every
    // single commit.
    fun refresh(){
        tableView.refresh()
    }
}


