package kathiresh.pandian.uiowa.surveys


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_surveymenu.*


class surveymenu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surveymenu)  // create list view
        val listView = findViewById<ListView>(R.id.listView_survey_list)
        val networkportal=FrontEndNetworkAndroidAPI()
        val surveylist = networkportal.frontEndGetSurveyList(this@surveymenu)
        val listItems = arrayOfNulls<String>(surveylist.size)
        var username:String=""

        val logout= findViewById<TextView>(R.id.logout)
        //logout gives pop up error warning
        logout.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View) {
                val alert = AlertDialog.Builder(this@surveymenu)

                // Builder
                with (alert) {
                    setTitle("Sign out")
                    setMessage("Are you sure you want to log out?")



                    setPositiveButton("Log out") { dialog, whichButton ->
                        //showMessage("display the game score or anything!")
                        //networkPortal.frontEndLogOutUser(user.userName, this@surveymenu)
                        val networkportal = FrontEndNetworkAndroidAPI()
                        val extras = intent.extras
                        if (extras != null) {
                            username = extras.getString("Username")
                            Log.d("Logout", "username"+" "+username)
                            networkportal.frontEndLogOutUser(username, this@surveymenu)
                            goto_login_page(view)
                            Log.d("Logout", "Logged out")
                            dialog.dismiss()


                        }
                    }

                    setNegativeButton("Cancel") {
                        dialog, whichButton ->
                        //showMessage("Close the game or anything!")
                        dialog.dismiss()
                    }
                }

                // Dialog
                val dialog = alert.create()
                dialog.show()


            }
        })
        //set listview adapter
        for (i in 0 until surveylist.size) {
            val survey = surveylist[i]
            listItems[i] = survey
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter



        listView.setOnItemClickListener{
            parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            Log.d("SurveyMenu", "Survey Selected")
            val networkportal=FrontEndNetworkAndroidAPI()
            val survey=networkportal.frontEndGetSurvey(listItems[position]!!, this@surveymenu)
            val Surveyadapter=SurveyController(survey)
            val responses:ArrayList<String> = ArrayList()
            username=intent.extras.getString("Username")
            Surveyadapter.prepareQuestion(listItems[position]!!,survey, 0, this@surveymenu, responses, username)

        }
    }
    fun goto_login_page(view: View) {
        val goto_login_page = Intent(this, MainActivity::class.java)
        startActivity(goto_login_page)
    }

    //same functionality as logout
    override fun onBackPressed() {
        logout.performClick()
    }

}



