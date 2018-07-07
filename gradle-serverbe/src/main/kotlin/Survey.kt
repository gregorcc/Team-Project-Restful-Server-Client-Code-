//tempory survey object, default value for surveyType is "Not specified"
package edu.uiowa
open class Survey{
    //private container of questions
    val questionList: ArrayList<String>
     val surveyName: String
     val surveyType:String
    constructor(surveyName: String, surveyType: String){
        this.surveyName = surveyName
        this.surveyType = surveyType
        questionList = ArrayList<String>()
    }
    constructor(surveyName:String){
        this.surveyName = surveyName
        this.surveyType = "Not Specified"
        questionList = ArrayList<String>()
    }

    //adds question as inputted by a String
    fun addQuestion(inputQuestion: String){
        questionList.add(inputQuestion)
    }

    //deletes a question based upon its question number
    //the function will delete the question inside of the arrayList
    //that is the index + 1
    fun deleteQuestionByNumber(index: Int){
        questionList.removeAt(index -1)
    }

    //deletes a question based upon a String literal,
    //the question will be found inside the arrayList and then deleted
    fun deleteQuestion(inputQuestion: String){
        questionList.remove(inputQuestion)
    }

    //formats the questions for when they are printed
    override fun toString(): String {
        var result = ""
        for(i in 0..questionList.size-1){
            result += "${i + 1}: ${questionList[i]}\n"
        }
        return result
    }

    //more explicit definition, this just prints a call to the
    //toString method
    fun displayQuestionsToConsole(){
        println(this.toString())
    }
}