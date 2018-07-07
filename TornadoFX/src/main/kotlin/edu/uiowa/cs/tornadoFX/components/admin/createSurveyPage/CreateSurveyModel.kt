package edu.uiowa.cs.tornadoFX.components.admin.createSurveyPage

import edu.uiowa.cs.tornadoFX.components.dataStructure.SurveyChoices
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

//This is the central data model for the view, it made both View and Controller point to same data address.
//So it simples access function.

class CreateSurveyModel : ItemViewModel<SurveyChoices>() {
    val surveyQuestion = bind { item?.surveyQuestionProperty() }
    val surveyChoices = bind { item?.choicesListProperty()  }
    val surveyTitle = bind { SimpleStringProperty() }
}
