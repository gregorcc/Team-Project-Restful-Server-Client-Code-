package edu.uiowa.cs.tornadoFX.components.admin.welcomePage

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage.CreateSurveyView
import edu.uiowa.cs.tornadoFX.components.admin.exportDataPage.ExportUserDataView
import edu.uiowa.cs.tornadoFX.components.admin.exportDataPage.ExportSurveyResponseView
import edu.uiowa.cs.tornadoFX.components.admin.JupyterView
import edu.uiowa.cs.tornadoFX.components.admin.importSurveyPage.ImportSurveyView
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterView
import edu.uiowa.cs.tornadoFX.components.user.loginPage.UserLoginView
import tornadofx.*

/*
*   @@author Layton Gao
*
*   class @AdminWelcomePageView is the welcome page for Admin
*   It will contains several button take function ImportData, ExportData, CreateNewSurvey
*
* */


class AdminWelcomePageView: MasterView("Welcome Admin!"){

    init {
        with(root){

            center = vbox(25) {

                label("Welcome Admin!")

                addClass(Styles.wrapper)

                button("Export Survey Response"){
                    action {
                        replaceWith(ExportSurveyResponseView::class)
                    }
                }
                button("Export User Data") {
                    action {
                        replaceWith(ExportUserDataView::class)
                    }
                }

                button("Import New Survey") {
                    action {
                        replaceWith(ImportSurveyView::class)
                    }
                }

                button("Create New Survey") {
                    shortcut("Enter")
                    action {
                        replaceWith(CreateSurveyView::class)
                    }
                }

                button("JupyterBook") {
                    action {
                        replaceWith(JupyterView::class)
                    }
                }
                button ("Log Out"){
                    action {
                        replaceWith(UserLoginView::class)
                    }
                }

            }
        }
    }
}