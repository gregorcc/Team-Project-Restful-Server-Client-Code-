package kathiresh.pandian.uiowa.surveys

import android.content.Context
import android.content.Intent
import android.util.Log


class SurveyController() {

    var Surveys:List<Any> = listOf()
    val surveyresponses: MutableList<String> = mutableListOf()
    var surveyqs = Surveys.listIterator()

    constructor(SurveyList: List<Any>): this(){
        Surveys=SurveyList
        surveyqs=Surveys.listIterator()
    }
    fun prepareQuestion(Survey:String, survey:List<Any>, count: Int, context: Context, responses:ArrayList<String>, username:String): Boolean {

        val question_num = count + 1

        val surveyvals = SurveyController(survey)
        var nextquestion: Any? = surveyvals.nextQuestion()
        for (x in 0..count - 1) {
            nextquestion = surveyvals.nextQuestion()
        }
        if (nextquestion == null){
            return true
        }
        else {
            //pass data to multiple choice or free response activity
            if (surveyvals.typeofquestion(nextquestion) == "mc") {
                //parse multiple choice Map to get question and possible answers
                val Question= (nextquestion as Map<*,*>)["first"] as String

                Log.d("SurveyTitle", "what"+Question)


                val Answers= (nextquestion)["second"] as List<String>
                Log.d("SurveyQuestions", "sup"+ Answers.toString())

                val listans: ArrayList<String> = ArrayList()
                listans.addAll(Answers)

                val myintent = Intent(context, multiple_choice::class.java)

                myintent.putExtra("Surveytitle", Survey)
                myintent.putExtra("SurveyQ", Question)
                myintent.putExtra("Count", question_num)
                myintent.putExtra("Username", username)
                myintent.putStringArrayListExtra("Choices", listans)
                myintent.putStringArrayListExtra("Responses", responses)
                myintent.putExtra("SurveyLength",survey.size)
                context.startActivity(myintent)

            } else {

                val myintent = Intent(context, free_response::class.java)
                myintent.putExtra("Survey", Survey)
                myintent.putExtra("SurveyQ", nextquestion.toString())
                myintent.putExtra("Count", question_num)
                myintent.putExtra("Username", username)
                myintent.putStringArrayListExtra("Responses", responses)
                myintent.putExtra("SurveyLength", survey.size)
                context.startActivity(myintent)

            }

        }
        return false
    }
    fun nextQuestion(): Any?{

        if (surveyqs.hasNext())
            return surveyqs.next()
        else
            return null
    }

    fun typeofquestion(question: Any?): String{
        var type=""
        when {
            question is String -> type = "fr"
            question is Map<*,*> -> type ="mc"
        }

        return type

    }


}




