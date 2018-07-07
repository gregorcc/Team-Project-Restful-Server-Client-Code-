package kathiresh.pandian.uiowa.surveys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_free_response.*

class free_response : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_free_response)


        val surveytitle = findViewById<TextView>(R.id.TextViewsurveyname)
        val surveyquestion = findViewById<TextView>(R.id.TextViewsurveyquestion)
        val next = findViewById<Button>(R.id.buttonnext)
        val surveyresponse=findViewById<EditText>(R.id.surveyresponse)
        val extras = intent.extras
        if (extras != null) {
            //get intents and update fields
            val Survey = extras.getString("Survey")
            surveytitle.text = Survey
            val Question = extras.getString("SurveyQ")
            surveyquestion.text = Question
            val SurveyLength=extras.getInt("SurveyLength")
            val Count = extras.getInt("Count")
            val Responses = extras.getStringArrayList("Responses")
            val username= extras.getString("Username")
            surveyquestionum.text=("Question"+(Count.toString())+"/"+(SurveyLength.toString()))

            fun validate(): Boolean{
                if (surveyresponse.text.isBlank()){
                    findViewById<TextView>(R.id.errortextview).text="Please Respond to Question"
                    return false
                }
                else return true
            }

            //validate input and add response to answer list (submit answer list if prepareNextQuestion is null
            next.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    if (validate()) {
                        val networkportal = FrontEndNetworkAndroidAPI()
                        val survey = networkportal.frontEndGetSurvey(surveytitle.text.toString(), this@free_response)
                        val Surveyadapter = SurveyController(survey)
                        Responses.add(surveyresponse.text.toString())
                        val finish = Surveyadapter.prepareQuestion(surveytitle.text.toString(), survey, Count, this@free_response, Responses, username)
                        if (finish) {
                            val thanks = Intent(this@free_response, thankyou::class.java)
                            startActivity(thanks)
                            networkportal.frontEndSubmitAnswers(username,Survey, Responses.toList(), this@free_response)
                        }
                    }

                }
            })
        }
        fun goto_survey_menu(view: View) {
            val goto_survey_menu = Intent(this, surveymenu::class.java)
            startActivity(goto_survey_menu)
        }

        val leave_survey = findViewById<TextView>(R.id.textViewleavesurvey)
        leave_survey.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                val alert = AlertDialog.Builder(this@free_response)
                with(alert) {
                    setTitle("Survey Menu")
                    setMessage("Are you sure you want to leave survey?")



                    setPositiveButton("Yes") { dialog, whichButton ->
                        //showMessage("display the game score or anything!")
                        //networkPortal.frontEndLogOutUser(user.userName, this@surveymenu)
                        goto_survey_menu(view)
                        dialog.dismiss()


                    }


                    setNegativeButton("No") { dialog, whichButton ->
                        //showMessage("Close the game or anything!")
                        dialog.dismiss()
                    }
                }

                // Dialog
                val dialog = alert.create()
                dialog.show()

            }
        })


    }
    override fun onBackPressed() {
        textViewleavesurvey.performClick()

    }
}

