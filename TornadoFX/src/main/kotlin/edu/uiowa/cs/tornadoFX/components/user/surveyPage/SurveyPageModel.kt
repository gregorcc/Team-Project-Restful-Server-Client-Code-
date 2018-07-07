package edu.uiowa.cs.tornadoFX.components.user.surveyPage

import javafx.beans.property.*
import tornadofx.*

/*
*   @@author Layton Gao
*
*   class @SurveyPageModel
*       Stored all the data, and can be access through View and Controller
*
* */

class SurveyPageModel: ViewModel(){
    var surveyTitle = bind { SimpleStringProperty() }
    var choicesList = bind{ SimpleListProperty<String>() }
    var choicesBoolean = bind{ SimpleBooleanProperty() }
    var response = bind { SimpleStringProperty() }
    var question = bind{ SimpleStringProperty() }
    var progress = bind { SimpleDoubleProperty() }
}