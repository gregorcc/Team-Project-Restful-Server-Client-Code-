package edu.uiowa.cs.tornadoFX.components.admin.exportDataPage

import edu.uiowa.cs.tornadoFX.components.admin.welcomePage.AdminWelcomePageView
import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.dataStructure.Person
import tornadofx.*
import java.time.LocalDate

/*
*   @@author Layton Gao
*
*   @ExportUserDataView is for export User Data, Admin can select what partial or full data to export.
*   Export button is hidden in right click.
*   It is also editable able so admin can edit before export.
*   Indeed, we don't want admin to update edited data to server. It isn't a good idea.
*
* */


class ExportUserDataView: View(){
    private val exportSurveyResponseController: ExportSurveyResponseController by inject()

    override val root = borderpane()
    private var personList = mutableListOf(
            Person("1", "Pacific Northwest", "USA", "p", LocalDate.now()),
            Person("2", "Alberta", "Canada", " etoi", LocalDate.now()),
            Person("3", "Midwest", "iaeooei", "ieotaoe", LocalDate.now())).observable()

    init {
        //todo: personList = exportSurveyResponseController.networkPortal.some_Random_Function_Throw_Me_A_List_Of_Person()
        with(root){
            addClass(Styles.wrapper)
            left = vbox {
                button("Export All User Data"){
                    setOnAction{
                        var sum =""
                        for (i in personList){
                            sum += i.toString()+"\n"
                        }
                        exportSurveyResponseController.createTxt(sum)
                    }
                }
                button("Add New User") {
                    action {
                        personList.add(Person("0", "", "", "mutable", LocalDate.now()))
                    }
                }
            }

            // Create a tableview  contains data <Person>,
            val table = tableview<Person> {
                isEditable = true
                multiSelect(true)
                items = personList
                column("First Name", Person::firstNameProperty).makeEditable()
                column("Last Name", Person::lastNameProperty).makeEditable()
                column("User Name", Person::userNameProperty).makeEditable()
                column("Age", Person::age)
                column("Date of Birth", Person::dateOfBirthProperty)

                contextmenu {
                    item("Send Email").action {
                        selectedItem?.apply { println("Sending Email to $name") }
                        //todo: send email or upload to server.
                    }
                    item("Export Data").action {
                        if (selectedItem!=null){
                            selectedItem?.apply {
                                setOnAction{

                                    // Create a row of string that separate by space and end by \n.
                                    // So Data Analysis Software, like SAS, R can import this kind data.
                                    var sum =""
                                    for (i in selectionModel.selectedItems){
                                        sum += i.toString()+"\n"
                                    }
                                    exportSurveyResponseController.createTxt(sum)
                                }
                            }
                        }
                    }
                }
            }
            center = table

            bottom {
                button("Go Back") {
                    action {
                        replaceWith(AdminWelcomePageView::class)
                    }
                }
            }
        }
    }
}
