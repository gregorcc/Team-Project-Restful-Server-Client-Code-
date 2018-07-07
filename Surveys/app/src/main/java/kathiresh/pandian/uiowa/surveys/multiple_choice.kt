package kathiresh.pandian.uiowa.surveys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import kotlinx.android.synthetic.main.multiple_choice.*




class multiple_choice : AppCompatActivity() {
    val tag = "SURVAPP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiple_choice)

        val surveytitle = findViewById<TextView>(R.id.TextViewsurveyname)
        val questiontitle = findViewById<TextView>(R.id.TextViewsurveyquestion)
        val next=findViewById<Button>(R.id.buttonnext)
        val surveyquestionnum=findViewById<TextView>(R.id.surveyquestionum)

        var username=" "

        val extras = intent.extras
        if (extras != null) {
            //set views to values passed by intents
            val Survey = extras.getString("Surveytitle")
            surveytitle.text = Survey
            val Question = extras.getString("SurveyQ")
            questiontitle.text = Question
            val Answers = extras.getStringArrayList("Choices")
            val Count = extras.getInt("Count")
            val SurveyLength=extras.getInt("SurveyLength")
            val Responses= extras.getStringArrayList("Responses")
            username= extras.getString("Username")
            surveyquestionnum.text=("Question"+(Count.toString())+"/"+(SurveyLength.toString()))
            val radnames = listOf(R.id.radio1, R.id.radio2, R.id.radio3, R.id.radio4, R.id.radio5, R.id.radio6,R.id.radio7,R.id.radio8)
            val radarray = Array<RadioButton>(8) { it -> findViewById(radnames.get(it)) }
            // initially make all radio buttons invisible
            radarray.forEach { it.visibility = View.INVISIBLE }
            for (x in 0 until Answers.size) {
                radarray[x].text = Answers[x]
                radarray[x].setVisibility(View.VISIBLE)
            }

            fun validate(): Boolean{
                if (radioGroup.checkedRadioButtonId==-1){
                    errortextview.text="Please Select a Response"
                    return false
                }
                else return true
            }
            next.setOnClickListener(object: View.OnClickListener{
                override fun onClick(view: View) {
                    if (validate()) {
                        val networkportal = FrontEndNetworkAndroidAPI()
                        val survey = networkportal.frontEndGetSurvey(surveytitle.text.toString(), this@multiple_choice)
                        val Surveyadapter = SurveyController(survey)
                        Responses.add(findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString())
                        val finish = Surveyadapter.prepareQuestion(surveytitle.text.toString(), survey, Count, this@multiple_choice, Responses,username)
                        if (finish) {
                            if (finish) {
                                val thanks = Intent(this@multiple_choice, thankyou::class.java)
                                startActivity(thanks)
                                networkportal.frontEndSubmitAnswers(username,Survey, Responses.toList(), this@multiple_choice)
                            }
                        }
                    }
                }
            })
        }


        val leave_survey = findViewById<TextView>(R.id.textViewleavesurvey)
        leave_survey.setOnClickListener(object : View.OnClickListener {

            override fun onClick(view: View) {

                val alert = AlertDialog.Builder(this@multiple_choice)
                with(alert) {
                    setTitle("Survey Menu")
                    setMessage("Are you sure you want to leave survey?")



                    setPositiveButton("Yes") { dialog, whichButton ->
                        Log.d("Logout","Logged out")
                        //showMessage("display the game score or anything!")

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


            fun goto_survey_menu(view: View) {
                val goto_survey_menu = Intent(this, surveymenu::class.java)
                startActivity(goto_survey_menu)
            }

            override fun onBackPressed() {
                textViewleavesurvey.performClick()
            }
        }


